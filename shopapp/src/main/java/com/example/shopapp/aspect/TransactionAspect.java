package com.example.shopapp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class TransactionAspect {

    @Around("@annotation(Tx)")
    public Object doTransaction(ProceedingJoinPoint pjp) throws Throwable{
            Object ret = null;
            try {
                log.info("Start Transaction...");
                ret = pjp.proceed();
                log.info("Commit Transaction...");
            } catch (Exception ex) {
                log.info("Rollback");
            }
            return  ret;
    }
}

/*
    @Tx
    public void doTask() {

    }

 */