package org.khu.logging;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ErrorLoggingAspect {


    @Pointcut("@annotation(ErrorLogging) || @within(ErrorLogging)")
    public void onError() {}

    @AfterThrowing(pointcut = "onError()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {

        LoggingUtil.error(joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), ex);

    }

}
