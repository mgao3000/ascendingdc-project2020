package com.ascendingdc.training.project2020.filter;//package com.ascending.training.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
@Order(20)
@WebFilter(filterName = "myFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class    MyFilter300 implements Filter {
//    @Autowired
//    private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        //do something before  pre execution
        logger.info("111111111111 pre action from MyFirstFilter, remoteHost={}", req.getRemoteHost());
        logger.info("111111111111 pre action from MyFirstFilter, RequestURI={}", req.getRequestURI());
        logger.info("111111111111 pre action from MyFirstFilter, Header={}", req.getHeader("testHeader"));

        //do something to keep the request flow going through the chain
        filterChain.doFilter(req, resp);

        //do something after post execution
        logger.info("11111111 post action from MyFirstFilter, status={}", resp.getStatus());
        logger.info("11111111 post action from MyFirstFilter, Headernames={}", resp.getHeaderNames());
    }
}
