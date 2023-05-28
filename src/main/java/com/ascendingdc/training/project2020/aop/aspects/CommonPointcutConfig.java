package com.ascendingdc.training.project2020.aop.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcutConfig {

    @Pointcut("execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter.JwtFilter)"
        + " && !within(com.ascendingdc.training.project2020.filter.SecurityFilter) && !within(com.ascendingdc.training.project2020.filter.JwtTokenFilter)")
    public void serviceAndDaoPackageConfig() {}

    @Pointcut("execution (* com.ascendingdc.training.project2020.service.*.*(..))")
    public void servicePackageConfig() {}

    @Pointcut("execution (* com.ascendingdc.training.project2020.dao.*.*(..))")
    public void daoPackageConfig() {}

    /*
     * pointcut for any bean with a name contains "Service" key word
     */
    @Pointcut("bean(*Service*)")
    public void allPackageConfigUsingBean() {}

    @Pointcut("@annotation(com.ascendingdc.training.project2020.aop.annotations.TrackTime)")
    public void trackTimeAnnotation() {}

    @Pointcut("@annotation(com.ascendingdc.training.project2020.aop.annotations.Loggable)")
    public void loggableAnnotation() {}
}
