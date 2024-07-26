package com.example.cih.common.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534531L;
    private int code;       // 추가로 지정

    public ItemNotFoundException(String message){
        super(message);
    }
}
