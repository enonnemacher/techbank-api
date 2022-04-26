package com.wipro.techbank.tests;

import com.wipro.techbank.domain.AccountType;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.domain.Transaction;
import com.wipro.techbank.dtos.TransactionRequestDto;

public class FactoryTransactions {

    public static final TransactionRequestDto TRANSACTION_REQUEST_DTO = createTransactionsRequestDto();
    public static final Transaction TRANSACTION_ENTITY = createTransacionsEntity();
    public static final Transaction TRANSACTION_ENTITY_AFTER_SAVE = createTransacionsEntityAfterSave();

    private static final Double DEPOSIT_VALUE = 200.00;

    public static TransactionRequestDto createTransactionsRequestDto() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(DEPOSIT_VALUE);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        return transactionRequestDto;
    }

    public static Transaction createTransacionsEntity() {
        Transaction transaction = new Transaction();
        transaction.setOperation(Operation.DEPOSIT);
        transaction.setValue(DEPOSIT_VALUE);
        transaction.setAccountType(FactoryTransactions.TRANSACTION_REQUEST_DTO.getAccountType());
        transaction.setAccount(FactoryAccounts.CHECKING_ACCOUNT_ENTITY);
        return transaction;
    }

    public static Transaction createTransacionsEntityAfterSave() {
        Transaction transaction = createTransacionsEntity();
        transaction.setId(1L);
        return transaction;
    }
}
