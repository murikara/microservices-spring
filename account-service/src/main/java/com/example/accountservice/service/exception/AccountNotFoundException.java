package com.example.accountservice.service.exception;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(long id){
        super("Account not found for " + id);
    }
}
