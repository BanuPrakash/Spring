package com.adobe.prj.aop;

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

import com.adobe.prj.service.NotFoundException;

@Component
@Aspect
public class LogAspect {
	Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* com.adobe.prj.service.*.*(..))")
	public void logBefore(JoinPoint jp) {
		logger.info("Called : " + jp.getSignature());
		Object[] args = jp.getArgs();
		for(Object arg: args) {
			logger.info("argument " + arg);
		}
	}
	
	@After("execution(* com.adobe.prj.service.*.*(..))")
	public void logAfter(JoinPoint jp) {
		logger.info("***********");
	}
	
	@Around("execution(* com.adobe.prj.service.*.*(..))")
	public Object doProfile(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = new Date().getTime();
			Object ret = pjp.proceed();
		long endTime = new Date().getTime();
		logger.info("Time :" + (endTime - startTime) + " ms");
		return ret;
	}

	@AfterThrowing(value="execution(* com.adobe.prj.service.*.*(..))", throwing = "ex")
	public void logException(JoinPoint jp, NotFoundException ex) {
		logger.info(jp.getSignature().getName() + " --> " + ex.getMessage());
	}
}
