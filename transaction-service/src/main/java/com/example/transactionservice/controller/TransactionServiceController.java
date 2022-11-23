package com.example.transactionservice.controller;

import com.example.transactionservice.dto.TransferDto;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.service.TransactionService;
import com.example.transactionservice.service.exception.InsufficientBalanceException;
import com.example.transactionservice.service.exception.UnableToUpdateAccountException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
@Data
public class TransactionServiceController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody @Valid TransferDto dto) throws InsufficientBalanceException, UnableToUpdateAccountException {
        return new ResponseEntity<Transaction>(transactionService.transfer(dto), HttpStatus.OK);
    }
}
