package com.example.transactionservice.service;

import com.example.transactionservice.dto.AccountDto;
import com.example.transactionservice.dto.TransferDto;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repo.TransactionRepository;
import com.example.transactionservice.service.exception.InsufficientBalanceException;
import com.example.transactionservice.service.exception.UnableToUpdateAccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;
    private final String accountServiceUrl = "http://account-service/account/";

    public Transaction transfer(TransferDto dto) throws InsufficientBalanceException, UnableToUpdateAccountException {
        AccountDto sender = getAccount(dto.getSender());
        AccountDto recipient = getAccount(dto.getRecipient());
        if(verify(sender, dto.getAmount())){
            log.info("Verified sender, starting transfer...");
            sender.setAmount(sender.getAmount() - dto.getAmount());
            recipient.setAmount(recipient.getAmount() + dto.getAmount());
            updateAccount(sender);
            updateAccount(recipient);
            log.info("Transaction completed");
        } else {
            throw new InsufficientBalanceException(sender.getId());
        }
        return transactionRepository.save(generate(dto.getAmount(), sender.getId(), recipient.getId()));
    }

    private Transaction generate(double amount, long sender, long recipient){
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setRecipient(recipient);
        transaction.setAmount(amount);
        transaction.setDateTime(LocalDateTime.now().toString());
        return transaction;
    }

    private AccountDto getAccount(long id){
        log.info("Getting account with ID {} and url {}", id, accountServiceUrl);
        return restTemplate.getForObject((accountServiceUrl + id), AccountDto.class);
    }

    private boolean verify(AccountDto accountDto, double amount){
        return accountDto.getAmount() - amount > 0;
    }

    private void updateAccount(AccountDto accountDto) throws UnableToUpdateAccountException {
        restTemplate.put((accountServiceUrl + accountDto.getId()), accountDto);
        log.info("Updated account successfully");
    }
}
