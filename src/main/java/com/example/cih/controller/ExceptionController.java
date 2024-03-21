package com.example.cih.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
@Log4j2
public class ExceptionController {

    @GetMapping("/RuntimeException")
    public void getRuntimeException() {

        log.error("test : RuntimeException");
         throw new RuntimeException("getRunTimeException 호출");
    }

    @GetMapping("/MethodArgumentNotValidException")
    public void getMethodArgumentNotValidException(@PathVariable String str) {

        // http://localhost:8090/exception/MethodArgumentNotValidException
        // 을 호출 하여 MissingPathVariableException 발생 시킴
        log.error(str);

    }
}
