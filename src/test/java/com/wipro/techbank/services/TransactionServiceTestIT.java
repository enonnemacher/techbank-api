package com.wipro.techbank.services;

import com.wipro.techbank.domain.AccountType;
import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.dtos.TransactionRequestDto;
import com.wipro.techbank.dtos.TransactionResponseExtractDto;
import com.wipro.techbank.dtos.TransactionResponseOperationDto;
import com.wipro.techbank.repositories.SpecialAccountRepository;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class TransactionServiceTestIT{

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SpecialAccountRepository specialAccountRepository;

    @BeforeEach
    public void setUp() {


    }

    @Test
    public void checkingAccountDepositShouldReturnNewBalanceWhenIdExists() {
        Double expectedBalance = 1900.00;
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);

        TransactionResponseOperationDto response = transactionService.deposit(1L, transactionRequestDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), expectedBalance);
    }

    @Test
    public void checkingAccountDepositShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.deposit(100L, transactionRequestDto);
        });
    }

    @Test
    public void checkingAccountWithdrawShouldUpdateBalanceWhenIdExistsAndBalanceGreaterThanWithdraw() {

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(1600.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        Long accountId = 5L;
        TransactionResponseOperationDto response = transactionService.withdraw(accountId, transactionRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), 100.00);
    }

    @Test
    public void checkingAccountWithdrawShouldUpdateBalanceWhenIdExistsAndBalanceEqualToWithdraw() {

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(1700.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        Long accountId = 5L;
        TransactionResponseOperationDto response = transactionService.withdraw(accountId, transactionRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), 0.00);
    }

    @Test
    public void checkingAccountWithDrawShouldReturnResourceNotFoundExceptionWhenWithdrawIsGreaterThanBalance() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        transactionRequestDto.setValue(1800.00);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.withdraw(3L, transactionRequestDto);
        });
    }

    @Test
    public void checkingAccountWithDrawShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        transactionRequestDto.setValue(1800.00);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.withdraw(100L, transactionRequestDto);
        });
    }

    @Test
    public void specialAccountDepositShouldReturnNewBalanceWhenIdExists() {
        Double expectedBalance = 1900.00;
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);
        transactionRequestDto.setValue(200.00);
        TransactionResponseOperationDto response = transactionService.deposit(7L, transactionRequestDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), expectedBalance);
    }

    @Test
    public void specialAccountDepositShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        transactionRequestDto.setValue(1800.00);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.deposit(100L, transactionRequestDto);
        });
    }

    @Test
    public void specialAccountDepositShouldReturnNewBalanceWhenIdExistsAndLimitUsedAndValueBiggerLimitUsed() {
        Long accountId = 6L;
        SpecialAccount specialAccount = specialAccountRepository.findById(accountId).get();

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(specialAccount.getBalance() + 400.00);
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);

        transactionService.withdraw(accountId, transactionRequestDto);

        transactionRequestDto.setValue(900.00);
        TransactionResponseOperationDto response = transactionService.deposit(accountId, transactionRequestDto);


        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), 500);
        Assertions.assertEquals(specialAccount.getCreditSpecialUsed(), 0.00);
    }

    @Test
    public void specialAccountDepositShouldReturnNewBalanceWhenIdExistsAndLimitUsedAndValueSmallerLimitUsed() {
        Long accountId = 6L;
        SpecialAccount specialAccount = specialAccountRepository.findById(accountId).get();

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(specialAccount.getBalance() + 400.00);
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);

        transactionService.withdraw(accountId, transactionRequestDto);

        transactionRequestDto.setValue(100.00);
        TransactionResponseOperationDto response = transactionService.deposit(accountId, transactionRequestDto);


        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), -300);
        Assertions.assertEquals(specialAccount.getCreditSpecialUsed(), 300);
    }

    @Test
    public void specialAccountDepositShouldReturnNewBalanceWhenIdExistsAndLimitUsedAndValueEqualsLimitUsed() {
        Long accountId = 6L;
        SpecialAccount specialAccount = specialAccountRepository.findById(accountId).get();

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(specialAccount.getBalance() + 400.00);
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);

        transactionService.withdraw(accountId, transactionRequestDto);

        transactionRequestDto.setValue(400.00);
        TransactionResponseOperationDto response = transactionService.deposit(accountId, transactionRequestDto);


        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), 0.00);
        Assertions.assertEquals(specialAccount.getCreditSpecialUsed(), 0.00);
    }

    @Test
    public void specialAccountWithdrawShouldUpdateBalanceWhenIdExistsAndBalanceGreaterThanWithdraw() {

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(1600.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        Long accountId = 5L;
        TransactionResponseOperationDto response = transactionService.withdraw(accountId, transactionRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), 100.00);
    }

    @Test
    public void specialAccountWithdrawShouldUpdateBalanceWhenIdExistsAndBalanceEqualToWithdraw() {

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(1700.00);
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);
        Long accountId = 6L;
        TransactionResponseOperationDto response = transactionService.withdraw(accountId, transactionRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), 0.00);
    }

    @Test
    public void specialAccountWithdrawShouldUpdateBalanceWhenIdExistsAndBalanceAndLimitGreaterToWithdraw() {
        Double balance = 1700.00;
        Double limitCredit = 800.00;

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(balance + limitCredit);
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);
        Long accountId = 6L;
        SpecialAccount specialAccount = specialAccountRepository.findById(accountId).get();
        TransactionResponseOperationDto response = transactionService.withdraw(accountId, transactionRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBalance(), -800.00);
        Assertions.assertEquals(specialAccount.getCreditSpecialUsed(), 800.00);
    }

    @Test
    public void specialAccountWithDrawShouldReturnResourceNotFoundExceptionWhenWithdrawIsGreaterThanBalance() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(8000.00);
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);

        Long accountId = 6L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.withdraw(accountId, transactionRequestDto);
        });
    }

    @Test
    public void specialAccountWithDrawShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.withdraw(100L, transactionRequestDto);
        });
    }
    @Test
    public void extractShouldReturnListWhenIdExists() {
        List<TransactionResponseExtractDto> response =  transactionService.extract(1L);
        Assertions.assertNotNull(response);
    }
}
