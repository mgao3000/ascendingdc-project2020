package com.ascendingdc.training.project2020.filter;//package com.ascending.training.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//@Order(1)
@WebFilter(filterName = "myFilter2", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
@Order(3)
public class MyFilter200 implements Filter {
//    @Autowired
//    private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        //do something before  pre execution
        logger.info("2222222222 pre action from MySecondFilter, remoteHost={}", req.getRemoteHost());
        logger.info("2222222222 pre action from MySecondFilter, RequestURI={}", req.getRequestURI());
        logger.info("2222222222 pre action from MySecondFilter, Header={}", req.getHeader("testHeader"));

//        int intValue = 10;
//        if(intValue > 0) {
//            resp.sendError(HttpStatus.UNAUTHORIZED.value());
//        } else {
            //do something to keep the request flow going through the chain
            filterChain.doFilter(req, resp);
//        }

        //do something after post execution
        logger.info("2222222222 post action from MySecondFilter, status={}", resp.getStatus());
        logger.info("2222222222 post action from MySecondFilter, Headernames={}", resp.getHeaderNames());
    }
}
