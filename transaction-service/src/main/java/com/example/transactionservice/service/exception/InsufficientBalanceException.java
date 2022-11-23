package com.example.transactionservice.service.exception;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(long id){
        super("Insufficient balance for ID: " + id);
    }
}
