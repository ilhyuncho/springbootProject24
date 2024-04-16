package com.example.cih.common.exception;

public class orderNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534531L;

    public orderNotFoundException(String message){
        super(message);
    }
}
