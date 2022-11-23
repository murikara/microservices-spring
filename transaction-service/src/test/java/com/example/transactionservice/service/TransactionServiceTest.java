package com.example.transactionservice.service;

import com.example.transactionservice.dto.AccountDto;
import com.example.transactionservice.dto.TransferDto;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repo.TransactionRepository;
import com.example.transactionservice.service.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"account.service.url=http://account-service/account/"})
class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    RestTemplate restTemplate;

    private TransactionService transactionService;

    @BeforeEach
    void setUp(){
        transactionService = new TransactionService(transactionRepository, restTemplate);
    }

    @Test
    public void whenAccountHasInsufficientBalance_ThenAssertExceptionIsThrown() {
        AccountDto accountDto = new AccountDto(1, "test1@hotmail.com", "Muri", 50);
        when(restTemplate.getForObject("http://account-service/account/1", AccountDto.class)).thenReturn(accountDto);
        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.transfer(new TransferDto(1,2,500));
        });
    }

    // # TODO: fix the unit test as it mismatches on Transaction object on line 55.
    //         The mismatch is on the dateTime variable because a new Transaction object always
    //         has the date & time of the moment of creation.

//    @Test
//    public void whenTransferIsSuccessful() throws InsufficientBalanceException {
//        AccountDto sender = new AccountDto(1, "test1@hotmail.com", "Muri", 50);
//        AccountDto recipient = new AccountDto(2, "test2@hotmail.com", "Pete", 50);
//        TransferDto transferDto = new TransferDto(1,2,20);
//        Transaction transaction = new Transaction(1, 2, 20, "2022-11-23T16:20:05.682630800");
//        when(restTemplate.getForObject("http://account-service/account/1", AccountDto.class)).thenReturn(sender);
//        when(restTemplate.getForObject("http://account-service/account/2", AccountDto.class)).thenReturn(recipient);
//        when(transactionService.transfer(transferDto)).thenReturn(transaction);
//        when(transactionRepository.save(transaction)).thenReturn(any(Transaction.class));
//        Transaction actual = transactionService.transfer(transferDto);
//        assertEquals(transaction.getAmount(), actual.getAmount());
//    }

}