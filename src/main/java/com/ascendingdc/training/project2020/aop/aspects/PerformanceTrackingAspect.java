package com.ascendingdc.training.project2020.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class PerformanceTrackingAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Around("execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter..*)")
    @Around("com.ascendingdc.training.project2020.aop.aspects.CommonPointcutConfig.trackTimeAnnotation()")
    public Object findExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //1. start a timer
        long startTimeMillis = System.currentTimeMillis();


        //2. execute the method
        Object returnValue = proceedingJoinPoint.proceed();

        //3. Stop the timer
        long stopTimeMillis = System.currentTimeMillis();

        long executionDuration = stopTimeMillis - startTimeMillis;

        logger.info("=== @Around Aspect -- {} Method executed in {}", proceedingJoinPoint, executionDuration);

        return returnValue;
    }

}
