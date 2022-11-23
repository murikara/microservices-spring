package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.repo.AccountRepository;
import com.example.accountservice.service.exception.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    private AccountService accountService;
    private List<Account> accountList;

    @BeforeEach
    void setUp(){
        accountService = new AccountService(accountRepository);
        accountList = new ArrayList<>();
        accountList.add(new Account("user1@hotmail.com", "Muri", 300));
        accountList.add(new Account("legend@hotmail.com", "Rizky", 200));
    }

    @Test
    void shouldReturnOneAccount() throws AccountNotFoundException {
        long id = 1;
        when(accountRepository.findById(id)).thenReturn(Optional.ofNullable(accountList.get(1)));
        Account account = accountService.get(1);
        assertEquals(accountList.get(1).getName(), account.getName());
    }

    @Test
    void shouldReturnListWithAllAccounts() {
        when(accountRepository.findAll()).thenReturn(accountList);
        List<Account> list = accountService.getAll();
        assertEquals(accountList.size(), list.size());
    }

    @Test
    void shouldSaveAccount() {
        Account account = new Account("user1@hotmail.com", "Muri", 800);
        when(accountRepository.save(account)).thenReturn(account);
        Account saved = accountService.save(account);
        assertEquals(account.getName(), saved.getName());
    }

    @Test
    void shouldSaveAllArtists() {
        when(accountRepository.saveAll(accountList)).thenReturn(accountList);
        List<Account> list = accountService.save(accountList);
        assertEquals(accountList.size(), list.size());
    }

    @Test
    void shouldUpdateAccount() {
        Account account = new Account("user1@hotmail.com", "Muri", 200);
        Account updatedAccount = new Account("user1@hotmail.com", "Muri", 800);
        when(accountRepository.findById((long)1)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(updatedAccount);
        Account result = accountService.update(updatedAccount, 1);
        assertEquals(updatedAccount.getAmount(), result.getAmount());
    }

    @Test
    void shouldDeleteArtist() {
        doNothing().when(accountRepository).deleteById((long)1);
        accountService.delete(1);
        verify(accountRepository, times(1)).deleteById((long)1);
    }
}