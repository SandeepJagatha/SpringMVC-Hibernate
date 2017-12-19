package com.springdemo.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	//setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	//setup pointcut declaration
	@Pointcut("execution(* com.springdemo.controller.*.*(..))")
	private void forControllerPackage(){ }
	
	@Pointcut("execution(* com.springdemo.service.*.*(..))")
	private void forServicePackage(){ }
	
	@Pointcut("execution(* com.springdemo.dao.*.*(..))")
	private void forDAOPackage(){ }
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow(){
		
	}
	
	//add @Before advice
	@Before("forAppFlow()")
	private void before(JoinPoint theJoinPoint){
		// display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		System.out.println(" ===> @Before " + theMethod);
		myLogger.info(" ===> @Before " + theMethod);
		
		// display the argument to the method
		
		//
		Object args[] = theJoinPoint.getArgs();
		
		for (Object object : args) {
			System.out.println(object);
		}
	}
	
	//add @AfterReturning advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult){

		String theMethod = theJoinPoint.getSignature().toShortString();
		System.out.println(" ===> @AfterReturning " + theMethod);

		System.out.println(" ===> @theResult " + theResult);
	}
	
	
}
