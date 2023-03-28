package com.xiaomi.prj.aspect;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xiaomi.prj.exceptions.ResourceNotFoundException;

@Component
@Aspect
public class LogAspect {
	private Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	// all methods of all classes in "com.xiaomi.prj.service" package
	// taking any argument (..)
	// (String) taking string as argument
	// first * is any return type
	// public / private / protect is optional, by default its public methods
	@Before("execution(* com.xiaomi.prj.service.*.*(..))")
	public void logBefore(JoinPoint jp) {
		logger.info("Called :" + jp.getSignature());
		Object[] args = jp.getArgs();
		for(Object arg : args) {
			logger.info("Argument : " + arg);
		}
	}
	
	@After("execution(* com.xiaomi.prj.service.*.*(..))")
	public void logAfter(JoinPoint jp) {
		logger.info("***************");
	}
	
	@Around("execution(* com.xiaomi.prj.service.*.*(..))")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = new Date().getTime();
			Object ret = pjp.proceed();
		long endTime = new Date().getTime();
		logger.info("Time : " + (endTime - startTime) + "ms");
		return ret;
	}
	
	@AfterThrowing(pointcut = "execution(* com.xiaomi.prj.service.*.*(..))", throwing = "ex")
	public void logException(JoinPoint jp, ResourceNotFoundException ex) {
		logger.info("Exception occured : " + ex.getMessage() + " for " + jp.getSignature());
	}
}
