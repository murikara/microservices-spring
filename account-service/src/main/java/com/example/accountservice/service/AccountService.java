package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.repo.AccountRepository;
import com.example.accountservice.service.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account get(long id) throws AccountNotFoundException{
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Account save(Account Account){
        return accountRepository.save(Account);
    }

    public List<Account> save(List<Account> accountList) {
        return accountRepository.saveAll(accountList);
    }

    public Account update(Account updatedAccount, long id){
        return accountRepository.findById(id)
                .map(account -> {
                    account.setName(updatedAccount.getName());
                    account.setAmount(updatedAccount.getAmount());
                    account.setEmail(updatedAccount.getEmail());
                    return accountRepository.save(account);
                })
                .orElseGet(() -> {
                    updatedAccount.setId(id);
                    return accountRepository.save(updatedAccount);
                });
    }

    public void delete(long id){
        accountRepository.deleteById(id);
    }
}
