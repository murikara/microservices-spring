package com.example.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private long sender;
    @NotNull
    private long recipient;
    @NotNull
    @Min(value = 1, message = "Amount should be positive")
    private double amount;
    @NotNull
    private String dateTime;

    public Transaction(){}

    public Transaction(long sender, long recipient, double amount, String dateTime) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.dateTime = dateTime;
    }
}
