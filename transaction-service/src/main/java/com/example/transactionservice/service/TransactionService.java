package com.example.transactionservice.service;

import com.example.transactionservice.dto.AccountDto;
import com.example.transactionservice.dto.TransferDto;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repo.TransactionRepository;
import com.example.transactionservice.service.exception.InsufficientBalanceException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.util.annotation.Nullable;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    public TransactionService(TransactionRepository transactionRepository, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    public Transaction transfer(TransferDto dto) throws InsufficientBalanceException {
        AccountDto sender = getAccount(dto.getSender());
        AccountDto recipient = getAccount(dto.getRecipient());
        if(verify(sender, dto.getAmount())){
            log.info("Verified sender, starting transfer...");
            sender.setAmount(sender.getAmount() - dto.getAmount());
            recipient.setAmount(recipient.getAmount() + dto.getAmount());
            log.info("Transaction completed");
        } else {
            throw new InsufficientBalanceException(sender.getId());
        }
        return transactionRepository.save(generate(dto.getAmount(), sender.getId(), recipient.getId()));
    }

    private Transaction generate(double amount, long sender,@Nullable long recipient){
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setRecipient(recipient);
        transaction.setAmount(amount);
        transaction.setDateTime(LocalDateTime.now().toString());
        return transaction;
    }

    private AccountDto getAccount(long id){
        String accountServiceUrl = "http://account-service/account/";
        log.info("Getting account with ID {} and url {}", id, accountServiceUrl);
        return restTemplate.getForObject((accountServiceUrl + id), AccountDto.class);
    }

    private boolean verify(AccountDto accountDto, double amount){
        return accountDto.getAmount() - amount > 0;
    }
}
