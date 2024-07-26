package com.example.cih.common.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534534L;
    private int code;       // 추가로 지정

    public BoardNotFoundException(String message){
        // 존재하지 않는 과정(ID)에 대해 API 사용자가 접근을 시도할때
        super(message);
    }
}