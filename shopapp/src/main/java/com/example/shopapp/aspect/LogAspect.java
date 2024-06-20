package com.example.shopapp.aspect;

import com.example.shopapp.service.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Before("execution(* com.example.shopapp.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        log.info("Called :" + jp.getSignature());
        Object[] args = jp.getArgs();
        for(Object arg: args) {
            log.info("Argument : " + arg);
        }
    }

    @After("execution(* com.example.shopapp.service.*.*(..))")
    public void logAfter() {
        log.info("***************");
    }

    @Around("execution(* com.example.shopapp.service.*.*(..))")
    public Object doProfile(ProceedingJoinPoint pjp) throws Throwable{
        long startTime = new Date().getTime();
            Object retValue = pjp.proceed();
        long endTime = new Date().getTime();
        log.info( pjp.getSignature() + "Time :  " + (endTime - startTime) + " ms");
        return  retValue;
    }

    @AfterThrowing(value = "execution(* com.example.shopapp.service.*.*(..))", throwing = "ex")
    public void handleExceptionLog(JoinPoint jp, EntityNotFoundException ex) {
        log.info(jp.getSignature() + " exception : " + ex.getMessage());
    }
}
