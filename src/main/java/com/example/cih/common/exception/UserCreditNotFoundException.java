package com.example.cih.common.exception;

public class UserCreditNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534532L;

    public UserCreditNotFoundException(String message){

        super(message);
    }
}
