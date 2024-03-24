package com.example.cih.common.aspect;

import com.example.cih.domain.car.Car;
import com.example.cih.dto.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class ReturnValueLoggingAspect {

    //@Before("execution(* *(com.example.cih.dto.PageRequestDTO,..))")

    @AfterReturning(pointcut = "execution(* com.example.cih.service..*.*(..))", returning = "retVals")
    public void printReturnObject(JoinPoint joinPoint, PageResponseDTO<?> retVals) throws Throwable{
        log.error( "ReturnObject : " + retVals.toString());
        //retVals : ProductResponseDTO(number=1, name=스프링 부트 jpa12, price=5000, stock=500)
    }

    @AfterThrowing(pointcut = "execution(* com.example.cih.service..*.*(..))", throwing="th")
    public void printThrowable(JoinPoint joinPoint, Throwable th) throws Throwable {
        log.error("Error message: {}", th.getMessage());
    }
}
