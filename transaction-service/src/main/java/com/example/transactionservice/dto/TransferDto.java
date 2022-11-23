package com.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    @NotNull
    private long sender;
    @NotNull
    private long recipient;
    @NotNull
    @Min(value = 1, message = "Amount should be positive")
    private double amount;
}
