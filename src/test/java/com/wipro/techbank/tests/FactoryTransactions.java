package com.wipro.techbank.tests;

import com.wipro.techbank.domain.AccountType;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.domain.Transaction;
import com.wipro.techbank.dtos.TransactionRequestDto;
import com.wipro.techbank.dtos.TransactionResponseDto;

import java.time.LocalDateTime;

public class FactoryTransactions {

    public static final TransactionRequestDto TRANSACTION_CHEKING_ACCOUNT_REQUEST_DTO = createTransactionsCheckingAccountRequestDto();
    public static final Transaction TRANSACTION_CHEKING_ACCOUNT_ENTITY = createTransacionsCheckingAccountEntity();
    public static final Transaction TRANSACTION_CHEKING_ACCOUNT_ENTITY_AFTER_SAVE = createTransacionsCheckingAccountEntityAfterSave();

    public static final TransactionRequestDto TRANSACTION_SPECIAL_ACCOUNT_REQUEST_DTO = createTransactionsSpecialAccountRequestDto();
    public static final Transaction TRANSACTION_SPECIAL_ACCOUNT_ENTITY = createTransacionsSpecialAccountEntity();
    public static final Transaction TRANSACTION_SPECIAL_ACCOUNT_ENTITY_AFTER_SAVE = createTransacionsSpecialAccountEntityAfterSave();

    public static final TransactionResponseDto TRANSACTION_RESPONSE_DTO = createTransactionsResponseDto();

    private static Double DEPOSIT_VALUE = 200.00;

    public static TransactionResponseDto createTransactionsResponseDto() {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        LocalDateTime createAt = LocalDateTime.of(2022, 04, 13, 0, 0);
        transactionResponseDto.setId(1L);
        transactionResponseDto.setCreateAt(createAt);
        transactionResponseDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        transactionResponseDto.setOperation(Operation.DEPOSIT);
        transactionResponseDto.setValue(DEPOSIT_VALUE);
        return transactionResponseDto;
    }

    public static TransactionRequestDto createTransactionsCheckingAccountRequestDto() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        return transactionRequestDto;
    }

    public static Transaction createTransacionsCheckingAccountEntity() {
        Transaction transaction = new Transaction();
        transaction.setOperation(Operation.DEPOSIT);
        transaction.setValue(200.00);
        transaction.setAccountType(FactoryTransactions.TRANSACTION_CHEKING_ACCOUNT_REQUEST_DTO.getAccountType());
        transaction.setAccount(FactoryAccounts.CHECKING_ACCOUNT_ENTITY);
        return transaction;
    }

    public static Transaction createTransacionsCheckingAccountEntityAfterSave() {
        Transaction transaction = createTransacionsCheckingAccountEntity();
        transaction.setId(1L);
        return transaction;
    }

    public static TransactionRequestDto createTransactionsSpecialAccountRequestDto() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.SPECIAL_ACCOUNT);
        return transactionRequestDto;
    }

    public static Transaction createTransacionsSpecialAccountEntity() {
        Transaction transaction = new Transaction();
        transaction.setOperation(Operation.DEPOSIT);
        transaction.setValue(200.00);
        transaction.setAccountType(TRANSACTION_SPECIAL_ACCOUNT_REQUEST_DTO.getAccountType());
        transaction.setAccount(FactoryAccounts.SPECIAL_ACCOUNT_ENTITY);
        return transaction;
    }

    public static Transaction createTransacionsSpecialAccountEntityAfterSave() {
        Transaction transaction = createTransacionsCheckingAccountEntity();
        transaction.setId(2L);
        return transaction;
    }
}
