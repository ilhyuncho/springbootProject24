package com.example.cih.common.exception;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@Log4j2
public class RestExceptionAdvice extends ResponseEntityExceptionHandler {

    // 사용자 지정 에러 처리
    @ExceptionHandler(value = {BoardNotFoundException.class})
    public ResponseEntity<?> handleBoardNotFound(BoardNotFoundException e, WebRequest request){

        log.error("RestExceptionAdvice - BoardNotFoundException!!! ");
        return super.handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(value = {ItemNotFoundException.class})
    public ResponseEntity<?> handleItemNotFound(ItemNotFoundException e, WebRequest request){

        log.error("RestExceptionAdvice - ItemNotFoundException!!! ");
        log.error(e.getMessage());
        return super.handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(value = {AlreadyRegisterException.class})
    public ResponseEntity<?> handleAlreadyRegisterException(AlreadyRegisterException e, WebRequest request){

        log.error("RestExceptionAdvice - AlreadyRegisterException!!! ");
        log.error(e.getMessage());
        return super.handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<?> handleBoardNotFound(NoSuchElementException e, WebRequest request){

        log.error("RestExceptionAdvice - NoSuchElementException!!! ");
        log.error(e.getMessage());
        return super.handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }


    // ResponseEntityExceptionHandler 클래스에서 기본 제공하는 메서드를 재 지정 함
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // 기존 방식으로 처리 되는 것 주석 처리.. stack trace 내용이 다 보여서 공통된 양식으로 보내줄 필요가 있음
        //return super.handleMissingPathVariable(ex, headers, status, request);
        log.error("RestExceptionAdvice - MissingPathVariableException!!! ");
        return super.handleExceptionInternal(
                ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    // HttpMessageNotReadableException은 ResponseEntityExceptionHandler 클래스에서 기본 제공하는 메서드를 재 지정 함
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //return super.handleHttpMessageNotReadable(ex, headers, status, request);

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "전달값 JSON parse error!!");

        log.error("RestExceptionAdvice - HttpMessageNotReadableException!!! ");
        log.error(ex.getMessage());
        return super.handleExceptionInternal(
                ex,
                errorMap,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    // BindException은 ResponseEntityExceptionHandler 클래스에서 기본 제공하는 메서드를 재 지정 함
    @Override
    protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error("RestExceptionAdvice - BindException!!! ");

        Map<String, String> errorMap = new HashMap<>();
        if (e.hasErrors()) {
            BindingResult bindingResult = e.getBindingResult();

            StringBuilder errorMessage = new StringBuilder();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMessage.append(fieldError.getField())
                        .append(" : ")
                        .append(fieldError.getCode())
                        .append("\n");
            });
            errorMap.put("message", errorMessage.toString());
        }

        return super.handleExceptionInternal(
                e,
                errorMap,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}
