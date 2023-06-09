package com.ascendingdc.training.project2020.filter;

import com.ascendingdc.training.project2020.util.JwtTokenUtil;

//import net.codejava.user.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
@WebFilter(filterName = "jwtTokenFilter", urlPatterns = {"/xxx/yyy"}, dispatcherTypes = {DispatcherType.REQUEST})
public class JwtTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
				HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getAccessToken(request);

		if (!jwtUtil.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

//		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);
	}

	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}

		return true;
	}

	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
	}

//	private void setAuthenticationContext(String token, HttpServletRequest request) {
//		UserDetails userDetails = getUserDetails(token);
//
//		UsernamePasswordAuthenticationToken
//			authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
//
//		authentication.setDetails(
//				new WebAuthenticationDetailsSource().buildDetails(request));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//	}
//
//	private UserDetails getUserDetails(String token) {
//		User userDetails = new User();
//		String[] jwtSubject = jwtUtil.getSubject(token).split(",");
//
//		userDetails.setId(Integer.parseInt(jwtSubject[0]));
//		userDetails.setEmail(jwtSubject[1]);
//
//		return userDetails;
//	}

	/*
	 * sample of bypass this filter
	 */
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//		return new AntPathMatcher().match("/api/v1/menus", request.getServletPath());
//	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return new AntPathMatcher().match("/proj2020/*", request.getServletPath());
	}
}
