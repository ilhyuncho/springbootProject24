package com.example.cih.common.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArgumentInvalidException extends RuntimeException{

    private static final long serialVersionUID = 4540524531L;
    private int code;       // 추가로 지정

    public ArgumentInvalidException(String message){
        super(message);
    }
}
