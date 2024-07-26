package com.example.cih.common.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OwnerCarNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534500L;
    private int code;       // 추가로 지정

    public OwnerCarNotFoundException(String message){
        super(message);
    }
}
