package com.example.cih.common.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Aspect
@Component
@Log4j2
@Order(1)
public class ExceptionLoggingAspect {

    @PostConstruct     // 스프링이 @Component 를 사용하여 빈 생성 직후 호출 됨
    public void init(){
        log.error("ExceptionLoggingAspect init~~~~~~~~~~~~~~~");
    }

    @AfterThrowing(pointcut = "execution(* com.example.cih.service..*.*(..))", throwing = "ex")
    public void printExceptionLogging(JoinPoint joinPoint, Exception ex) {
        // service 에서 발생하는 에러 내용 출력
        log.error("Service {} 발생, {}", ex.getClass().getSimpleName(), joinPoint.getSignature().toShortString());
        log.error("ServiceMessage: {}",  ex.getMessage());
    }

    @AfterThrowing(pointcut = "execution(* com.example.cih.controller..*.*(..))", throwing = "ex")
    public void printControllerExceptionLogging(JoinPoint joinPoint, Exception ex) {
        // controller 에서 발생하는 에러 내용 출력
        log.error("Controller {} 발생, {}", ex.getClass().getSimpleName(), joinPoint.getSignature().toShortString());
        log.error("Controller Message: {}",  ex.getMessage());
    }
}
