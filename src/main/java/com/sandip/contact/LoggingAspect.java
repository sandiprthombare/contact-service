/**
 * 
 */
package com.sandip.contact;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sandip
 *
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

	@Pointcut("execution(* com.sandip.contact.c*.*.*(..))")
	protected void loggingOperation() {
	}
	@Pointcut("execution(* com.sandip.contact.s*.*.*(..))")
	protected void loggingOperationService() {
	}

	/**
	 * Advice executes before execution of any method
	 * @param joinPoint
	 */
	@Before("loggingOperation()")
	@Order(1)
	public void logBeforeJoinPoint(JoinPoint joinPoint) {
		log.info("Before Execution of Class : {}, Method Signature : {}, Arguments : {}",
				joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
				Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("loggingOperationService()")
	@Order(1)
	public void logBeforeJoinPointService(JoinPoint joinPoint) {
		log.info("Before Execution of Class : {}, Method Signature : {}, Arguments : {}",
				joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
				Arrays.toString(joinPoint.getArgs()));
	}
	
	/**
	 * Advice executes after execution of any method
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(value="loggingOperationService()", returning ="result")
	@Order(2)
	public void logAfterJoinPointService(JoinPoint joinPoint, Object result) throws Throwable{
		log.info("After Execution of Class {}, Method Signature : {}, returns {}",
				joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
				result!=null?result.toString():null);
	}
	
	@AfterReturning(value="loggingOperation()", returning ="result")
	@Order(2)
	public void logAfterJoinPoint(JoinPoint joinPoint, Object result) throws Throwable{
		log.info("After Execution of Class {}, Method Signature : {}, returns {}",
				joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
				result!=null?result.toString():null);
	}
	
//	@AfterThrowing(pointcut="loggingOperation()", throwing = "e")
//	@Order(3)
//	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
//		log.error("An exception has been thrown in Class : {}, Method Signature : {}, Exception Details : {} ",joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),e.getMessage());
//		e.printStackTrace();
//	}
//	
	@AfterThrowing(pointcut="loggingOperationService()", throwing = "e")
	@Order(3)
	public void logAfterThrowingService(JoinPoint joinPoint, Throwable e) {
		log.error("An exception has been thrown in Class : {}, Method Signature : {}, Exception Details : {} ",joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),e.getMessage());
		e.printStackTrace();
	}
}
