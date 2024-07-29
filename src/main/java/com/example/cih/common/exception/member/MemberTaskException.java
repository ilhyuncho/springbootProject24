package com.example.cih.common.exception.member;

import lombok.Getter;

@Getter
public class MemberTaskException extends RuntimeException{
    private final String msg;
    private final int code;

    public MemberTaskException(String msg, int code){
        this.msg = msg;
        this.code = code;
    }
}
