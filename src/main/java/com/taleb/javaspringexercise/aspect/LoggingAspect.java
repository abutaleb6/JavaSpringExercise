package com.taleb.javaspringexercise.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.taleb.javaspringexercise.controller..*(..))")
    public Object logRequestsAndResponses(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method: {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        logger.info("Response: {}", result);
        return result;
    }

    @Before("execution(* com.taleb.javaspringexercise.controller..*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logger.info("Entering method {}.{}() with arguments {}",
        joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
        logger.info("HTTP Method : {}", request.getMethod());
        logger.info("Request URI : {}", request.getRequestURI());
        logger.info("IP Address : {}", request.getRemoteAddr());
        logger.info("User Agent : {}", request.getHeader("User-Agent"));
    }

    @AfterReturning(pointcut = "execution(* com.taleb.javaspringexercise.controller..*(..))", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method {}.{}() with result {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }
}
