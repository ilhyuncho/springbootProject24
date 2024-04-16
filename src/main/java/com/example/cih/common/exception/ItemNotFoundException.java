package com.example.cih.common.exception;

public class ItemNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 4540534531L;

    public ItemNotFoundException(String message){
        super(message);
    }
}
