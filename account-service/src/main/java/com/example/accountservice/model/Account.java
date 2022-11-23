package com.example.accountservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String name;
    @NotNull
    @Min(value = 1, message = "Amount should be positive")
    private double amount;

    public Account(String email, String name, double amount) {
        this.email = email;
        this.name = name;
        this.amount = amount;
    }
}
