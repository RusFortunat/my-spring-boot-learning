package com.example.demoAOP.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);
    private static final ThreadLocal<Long> executionTime = new ThreadLocal<>();

    @Around("execution(* com.example.demoAOP.service.ContainerOfObjects.getRandomIntElement(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.nanoTime();
        Object result = joinPoint.proceed();
        final long end = System.nanoTime();
        executionTime.set(end - start);

        log.info("Execution time of {}.{} method is: {} nanoseconds",
            joinPoint.getSignature().getDeclaringType(),
            joinPoint.getSignature().getName(),
            end - start);
        log.info("Invoked integer: {}", result);

        return result;
    }

    public Long getLastExecutionTime() {
        return executionTime.get();
    }

}
