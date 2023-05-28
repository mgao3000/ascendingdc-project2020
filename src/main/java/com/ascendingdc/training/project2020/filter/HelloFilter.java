package com.ascendingdc.training.project2020.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "helloFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
@Order(1)
public class HelloFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("==========, HelloFilter preprocessing before moving forward, getRemoteHost={}", servletRequest.getRemoteHost());
        String remoteHost = servletRequest.getRemoteHost();
//        if(remoteHost == null)
            filterChain.doFilter(servletRequest, servletResponse);


        logger.info("==========, HelloFilter postprocessing before return back to caller, getContentType={}",servletResponse.getContentType());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
