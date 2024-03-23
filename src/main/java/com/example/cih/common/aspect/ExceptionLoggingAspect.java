package com.example.cih.common.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
@Order(1)
public class ExceptionLoggingAspect {
    @AfterThrowing(pointcut = "execution(* com.example.cih.service..*.*(..))", throwing = "ex")
    public void printExceptionLogging(JoinPoint joinPoint, Exception ex) {

        // service 에서 발생하는 에러 내용 출력
        log.error("{} 발생, {}", ex.getClass().getSimpleName(), joinPoint.getSignature().toShortString());
        log.error("Message: {}",  ex.getMessage());
    }
}
