package com.example.accountservice.controller;

import com.example.accountservice.model.Account;
import com.example.accountservice.repo.AccountRepository;
import com.example.accountservice.service.AccountService;
import com.example.accountservice.service.exception.AccountNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
@Data
public class AccountServiceController {
    private final AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accountList = accountService.getAll();
        if(accountList.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody @Valid Account account){
        return new ResponseEntity<>(accountService.save(account), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@RequestBody @Valid Account updatedAccount, @PathVariable long id){
        return new ResponseEntity<>(accountService.update(updatedAccount, id), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable long id){
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
