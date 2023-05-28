package com.ascendingdc.training.project2020.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * this should a configuration class with AOP configuration
 */
@Configuration
@Aspect   //What + When = Aspect
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //Pointcut - When?
    // Pointcut syntax:  execution(* PACKAGE.*.*(..))  for any
    //return datatype, any package
    //example: execution (* com.ascendingdc.training.project2020.service.*.*(..))
    // to intercept all spring beans within that package
//    @Pointcut("execution (* com.ascendingdc.training.project2020.service.*.*(..))")
//    @Before("execution (* com.ascendingdc.training.project2020.service.*.*(..))")
//    @Before("execution (* com.ascendingdc.training.project2020.dao.*.*(..))")
//    @Before("execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter..*)")   //define when to apply the code below
    @Before("execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter.JwtFilter)"
        + " && !within(com.ascendingdc.training.project2020.filter.SecurityFilter)"
        + " && !within(com.ascendingdc.training.project2020.filter.JwtTokenFilter)")   //define when to apply the code below
//    @Before("com.ascendingdc.training.project2020.aop.aspects.CommonPointcutConfig.serviceAndDaoPackageConfig()")   //define when to apply the code below
    public void logMethodCallBeforeExecution(JoinPoint joinPoint) {
        // Logic - What to do?
        logger.info("===========### Before Aspect Method is called - {}", joinPoint);  //define what to do?
        logger.info("===========### Before Aspect Method is called - {} is called with arguments: {}", joinPoint, joinPoint.getArgs());
    }

//    @After("execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter..*)")   //define when to apply the code below
    @After("com.ascendingdc.training.project2020.aop.aspects.CommonPointcutConfig.allPackageConfigUsingBean())")   //define when to apply the code below
    public void logMethodCallAfterExecution(JoinPoint joinPoint) {
        // Logic - What to do?
        logger.info("=========== After Aspect Method has executed - {}", joinPoint);  //define what to do?
        logger.info("=========== After Aspect Method has executed - {} is called with arguments: {}", joinPoint, joinPoint.getArgs());
    }

//    @AfterThrowing(
//            pointcut = "execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter..*)",
//            throwing = "exception")
    @AfterThrowing(
        pointcut = "execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter.JwtFilter)"
            + " && !within(com.ascendingdc.training.project2020.filter.SecurityFilter) && !within(com.ascendingdc.training.project2020.filter.JwtTokenFilter)",
        throwing = "exception")
    public void logMethodCallAfterException(JoinPoint joinPoint, Exception exception) {
        // Logic - What to do?
        logger.info("=========== After Throwing Aspect -- {} has thrown an exception  - {}", joinPoint, exception.getMessage());  //define what to do?
    }

//    @AfterReturning(
//            pointcut = "execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter..*)",
//            returning = "resultVale")
    @AfterReturning(
        pointcut = "execution (* com.ascendingdc.training.project2020.*.*.*(..)) && !within(com.ascendingdc.training.project2020.filter.JwtFilter)"
            + " && !within(com.ascendingdc.training.project2020.filter.SecurityFilter) && !within(com.ascendingdc.training.project2020.filter.JwtTokenFilter)",
        returning = "resultVale")
    public void logMethodCallAfterSuccessfulExecution(JoinPoint joinPoint, Object resultVale) {
        // Logic - What to do?
        logger.info("=========== After Returning Aspect -- {} has returned  - {}", joinPoint, resultVale);  //define what to do?
    }

    @Around("com.ascendingdc.training.project2020.aop.aspects.CommonPointcutConfig.loggableAnnotation()")
    public Object doLoggingAroundMethodExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //1. start logging something before execute a method
        logger.info("===== @Around Aspect -- before method execution, Now start logging something to test Loggable annotation as a pointcut");


        //2. execute the method
        Object returnValue = proceedingJoinPoint.proceed();

        //3. do some logging after the method execution
        logger.info("===== @Around Aspect -- after method execution, Now start logging something to test Loggable annotation as a pointcut");

        return returnValue;
    }


}
