package com.bk.view.stopwatch;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StopWatchAspect {

	@Around("@annotation(com.bk.view.stopwatch.StopWatch)")
	public Object stopWatch(ProceedingJoinPoint joinPoint) throws Throwable {
		Object proceed = null;
		long startTime = System.currentTimeMillis();
		try {
			proceed = joinPoint.proceed();
		}finally {
			long endtime = System.currentTimeMillis();
			String msg = String.format("Class Name: %s. Method Name: %s. Time taken for Execution is : %s",
						joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(),
						(endtime-startTime)+"ms");

			System.out.println("[StopWatch] " + msg);
		}

		return proceed;

	}

}