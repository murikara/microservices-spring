package com.example.transactionservice.service.exception;

public class UnableToUpdateAccountException extends Exception{
    public UnableToUpdateAccountException(long id) {
        super("Unable to update account for ID: " + id);
    }
}
