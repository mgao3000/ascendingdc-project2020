package com.ascendingdc.training.project2020.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "test", urlPatterns = {"/world/hello/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class HelloWorldFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("======$$$$$$$$$$$$$$$$$$ HelloWorldFilter Before the filter chain!!!");

        filterChain.doFilter(servletRequest, servletResponse);

        logger.info("======$$$$$$$$$$$$$$$$$$ HelloWorldFilter After the filter chain!!!");


    }

    @Override
    public void destroy() {

    }
}
