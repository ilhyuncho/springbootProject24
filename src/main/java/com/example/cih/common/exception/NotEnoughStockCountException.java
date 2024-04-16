package com.example.cih.common.exception;


public class NotEnoughStockCountException extends RuntimeException{

    private static final long serialVersionUID = 4540534532L;

    public NotEnoughStockCountException(String message){

        super(message);
    }
}
