package com.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private long id;
    private String email;
    private String name;
    @Min(value = 1, message = "Amount should be positive")
    private double amount;
}
