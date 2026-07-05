package org.khu.logging;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@ErrorLogging
@Aspect
@Component
@RequiredArgsConstructor
public class DebugLoggingAspect {


    @Pointcut("@annotation(DebugLogging) || @within(DebugLogging)")
    public void onDebug() {}

    @Around("onDebug()")
    public Object debug(ProceedingJoinPoint joinPoint) throws Throwable {

        Class<?> clazz = joinPoint.getTarget().getClass();

        String method = joinPoint.getSignature().getName();

        String[] paramNames = ( (MethodSignature) joinPoint.getSignature()).getParameterNames();

        Object[] args = joinPoint.getArgs();

        long start = System.currentTimeMillis();

       // LoggingUtil.debug(clazz, method, "Before processing method", "Before processing method");

        LoggingUtil.debug(clazz, method + " INPUT", LoggingInputSummaryUtil.summarize(paramNames, args));


        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;

        LoggingUtil.debug(clazz, method + "OUTPUT", LoggingSummaryUtil.summarize(result));

        LoggingUtil.debug(clazz, method + "DURATION", duration + " ms");

        return result;

    }

}
