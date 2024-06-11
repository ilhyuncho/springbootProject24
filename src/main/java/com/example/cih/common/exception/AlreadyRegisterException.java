package com.example.cih.common.exception;

public class AlreadyRegisterException extends RuntimeException{

    private static final long serialVersionUID = 4540534531L;

    public AlreadyRegisterException(String message){
        super(message);
    }
}
