package com.example.cih.common.exception;

public class OwnerCarNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534500L;

    public OwnerCarNotFoundException(String message){
        super(message);
    }
}
