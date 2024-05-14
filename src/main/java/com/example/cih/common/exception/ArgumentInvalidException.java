package com.example.cih.common.exception;

public class ArgumentInvalidException extends RuntimeException{

    private static final long serialVersionUID = 4540524531L;

    public ArgumentInvalidException(String message){
        super(message);
    }
}
