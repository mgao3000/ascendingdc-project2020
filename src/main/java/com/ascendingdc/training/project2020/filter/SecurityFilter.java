/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.filter;

//import com.ascendingdc.training.project2020.entity.User;
import com.ascendingdc.training.project2020.dto.UserDto;
import com.ascendingdc.training.project2020.service.JWTService;
import com.ascendingdc.training.project2020.service.UserService;
import com.ascendingdc.training.project2020.service.impl.JWTServiceImpl;
import com.ascendingdc.training.project2020.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/xxx/*"}, dispatcherTypes = {DispatcherType.REQUEST})
//public class SecurityFilter implements Filter {
public class SecurityFilter extends OncePerRequestFilter {
//    @Autowired private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    private static String AUTH_URI = "/auth";  //"/hi";  //"/auth";

//    @Override
//    public void init(FilterConfig filterConfig) {
//        // TODO Auto-generated method stub
//    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
////        HttpServletRequest req = (HttpServletRequest)request;
////        int statusCode = authorization(req);
////        if (statusCode == HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(request, response);
////        else ((HttpServletResponse)response).sendError(statusCode);
////        filterChain.doFilter(request, response);
//
//  //==========================================================
//        HttpServletRequest req = (HttpServletRequest)request;
//        HttpServletResponse resp = (HttpServletResponse)response;
//
/////        int statusCode = authorization(req);
//        int statusCode = authorization(req);
//        if (statusCode == HttpServletResponse.SC_ACCEPTED)
//            filterChain.doFilter(request, response);
//        else
//            resp.sendError(statusCode);
////        filterChain.doFilter(request, response);
//
//
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        int statusCode = authorization(req);
        if (statusCode == HttpServletResponse.SC_ACCEPTED)
            filterChain.doFilter(request, response);
        else
            resp.sendError(statusCode);
    }

    public void destroy() {
        // TODO Auto-generated method stub
    }

    private int authorization_firstVersion(HttpServletRequest req) {
//        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        int statusCode = HttpServletResponse.SC_ACCEPTED;
        String uri = req.getRequestURI();
        logger.info("###$$$%%%, uri={}", uri);
//        if (uri.equalsIgnoreCase(AUTH_URI))
//            statusCode = HttpServletResponse.SC_ACCEPTED;
        return statusCode;
    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;

        String uri = req.getRequestURI();
        if (uri.equalsIgnoreCase(AUTH_URI))
            return HttpServletResponse.SC_ACCEPTED;

        String httpMethodValue = req.getMethod();
        try {
            String wholeTokenString = req.getHeader("Authorization");
//            String token = wholeTokenString.replaceAll("^(.*?) ", "");
            String token = wholeTokenString.split(" ")[1].trim();
            logger.info("====== retrieved JWT token={}", token);
            if (token == null || token.trim().isEmpty())
                return statusCode;

//            Claims claims = JwtUtil.decodeJwtToken(token);
            Claims claims = jwtService.decryptJwtToken(token);
            logger.info("===== after parsing JWT token, claims.getId()={}", claims.getId());
            //TODO pass username and check role
            if(claims.getId()!=null){
                UserDto userDto = userService.getUserById(Long.valueOf(claims.getId()));
                logger.info("====== Now using userId={}, retrieved userDto={}", claims.getId(), userDto);
                if(userDto == null)
                    return statusCode;
//                else
//                    return HttpServletResponse.SC_ACCEPTED;
//                if(u==null)  statusCode = HttpServletResponse.SC_ACCEPTED;
            }


            String allowedResources = "";
            switch(httpMethodValue) {
                case "GET"    :
                    allowedResources = (String)claims.get("allowedReadResources");
                    break;
                case "POST"   :
                    allowedResources = (String)claims.get("allowedCreateResources");
                    break;
                case "PUT"    :
                    allowedResources = (String)claims.get("allowedUpdateResources");
                    break;
                case "PATCH"    :
                    allowedResources = (String)claims.get("allowedUpdateResources");
                    break;
                case "DELETE" :
                    allowedResources = (String)claims.get("allowedDeleteResources");
                    break;
            }

            String [] allowedResourceArray = allowedResources.split(",");
            for (String eachAllowedResourceString : allowedResourceArray) {
                logger.info("========================, uri={},  eachAllowedResourceString={}", uri, eachAllowedResourceString);
                if (uri.trim().toLowerCase().startsWith(eachAllowedResourceString.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }

            logger.debug(String.format("Verb: %s, allowed resources: %s", httpMethodValue, allowedResources));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return statusCode;
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return true;
////        return new AntPathMatcher().match("/proj2020/*", request.getServletPath());
//    }


}