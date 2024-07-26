package com.example.cih.common.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534532L;
    private int code;       // 추가로 지정

    public UserNotFoundException(String message){

        super(message);
    }
}
