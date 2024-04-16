package com.example.cih.common.exception;


public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534532L;

    public UserNotFoundException(String message){

        super(message);
    }
}
