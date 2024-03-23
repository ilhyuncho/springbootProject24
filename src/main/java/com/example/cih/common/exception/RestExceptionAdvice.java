package com.example.cih.common.exception;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class RestExceptionAdvice extends ResponseEntityExceptionHandler {
    
    // 사용자 지정 에러 처리
    @ExceptionHandler(value = {BoardNotFoundException.class})
    public ResponseEntity<?> handleBoardNotFound(BoardNotFoundException e, WebRequest request){

        log.error("RestExceptionAdvice~~~: handleCourceNotFound()");
        return super.handleExceptionInternal(
                e,
                e.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException e, WebRequest request){

        log.error("RuntimeException~~~: RuntimeException()");
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


        // 기존 방식으로 처리 함.. stack trace 내용이 다 보여서 공통된 양식으로 보내줄 필요가 있음
        //return super.handleMissingPathVariable(ex, headers, status, request);

        log.error("handleMissingPathVariable()~~");

        return super.handleExceptionInternal(
                ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}
