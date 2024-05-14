package com.example.cih.common.aspect;

import com.example.cih.common.exception.ArgumentInvalidException;
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

//    @Before("execution(* com.example.cih.controller..*.*(com.example.cih.dto.PageRequestDTO,..))")
//    // PageRequestDTO 인자를 받는 모든 메서드가 대상
//    public void printPageRequestDTOArgument(JoinPoint joinPoint) {
//
//        String argumentValue = Arrays.stream(joinPoint.getArgs())
//                .filter(obj-> PageRequestDTO.class.equals(obj.getClass()))  // 인자 중 같은 클래스 타입인 객체만 필터링
//                .findFirst()
//                .map(PageRequestDTO.class::cast)
//                .map(PageRequestDTO -> PageRequestDTO.toString())
//                .orElseThrow(() -> new ArgumentInvalidException("전달값이 Logging중 오류 발생!!!"));
//        log.error("2-1." + joinPoint.getSignature().toShortString());
//        log.error("2-1.Argument info : {}", argumentValue);
//    }

    //@Before("execution(* com.example.cih.controller.myPage..*.*(..))")
//    @Before("execution(* com.example.cih.controller.myPage..*.*(..))")
//    public void printMyPageArgument(JoinPoint joinPoint) {
//        // 모든 Argument 값 출력
//        log.error("2-2.Aspect - " + joinPoint.getSignature().toShortString());
//        Arrays.stream(joinPoint.getArgs())
//                .forEach(obj ->  log.error("2-2.Argument info: value: {}, type: {}", obj.toString(), obj.getClass()));
//    }

    @Before("execution(* com.example.cih.controller..register*(..))")
    public void printRegisterArgument(JoinPoint joinPoint) {
        // register 명령의 모든 Argument 값 출력
        log.error("2-3.Aspect - " + joinPoint.getSignature().toShortString());
        Arrays.stream(joinPoint.getArgs())
                .forEach(obj ->  log.error("2-3.Argument info: value: {}, type: {}", obj.toString(), obj.getClass()));
    }
}