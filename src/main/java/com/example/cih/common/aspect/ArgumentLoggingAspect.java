package com.example.cih.common.aspect;

import com.example.cih.dto.PageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Log4j2
@Order(1)
public class ArgumentLoggingAspect {

    // @Before 어드바이스는 코드 레벨에서 대상 객체의 메서드를 호출하지 않아도 된다.
    // 프록시 객체가 어드바이스의 코드를 실행한 후 대상 객체의 메서드를 호출하므로 어드바이스에 ProceedingJoinPoint를 주입받을 필요 없다

    @Before("execution(* *(com.example.cih.dto.PageRequestDTO,..))")
    // PageRequestDTO 인자를 받는 모든 메서드가 대상
    public void printCourceRequestArgument(JoinPoint joinPoint) {

        String argumentValue = Arrays.stream(joinPoint.getArgs())
                .filter(obj-> PageRequestDTO.class.equals(obj.getClass()))  // 인자 중 같은 클래스 타입인 객체만 필터링
                .findFirst()
                .map(PageRequestDTO.class::cast)
                .map(PageRequestDTO -> PageRequestDTO.toString())
                .orElseThrow();


        log.error(joinPoint.getSignature().toShortString());
        log.error("Argument info : {}", argumentValue);
    }

}