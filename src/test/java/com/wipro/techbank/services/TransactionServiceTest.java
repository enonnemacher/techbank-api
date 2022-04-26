package com.wipro.techbank.services;

import com.wipro.techbank.domain.AccountType;
import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.domain.Transaction;
import com.wipro.techbank.dtos.TransactionRequestDto;
import com.wipro.techbank.dtos.TransactionResponseOperationDto;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.repositories.SpecialAccountRepository;
import com.wipro.techbank.repositories.TransactionRepository;
import com.wipro.techbank.tests.FactoryAccounts;
import com.wipro.techbank.tests.FactoryTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest extends TestsServiceAbstract{

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CheckingAccountRepository checkingAccountRepository;

    @Mock
    private SpecialAccountRepository specialAccountRepository;


    @BeforeEach
    public void setUp() {
        CheckingAccount checkingAccount = FactoryAccounts.CHECKING_ACCOUNT_ENTITY;
        checkingAccount.setId(getExistsId());
        Mockito.when(checkingAccountRepository.getById(getExistsId())).thenReturn(FactoryAccounts.CHECKING_ACCOUNT_ENTITY);
        Mockito.when(transactionRepository.save(FactoryTransactions.TRANSACTION_ENTITY)).thenReturn(FactoryTransactions.TRANSACTION_ENTITY_AFTER_SAVE);
    }
    @Test
    @Override
    public void findAllShouldReturnPage() {

    }

    @Test
    @Override
    public void findByIdShouldReturnDtoWhenIdExixts() {

    }

    @Test
    @Override
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {

    }

    @Test
    @Override
    public void createShouldReturnDto() {

    }

    @Test
    @Override
    public void deleteShouldThrowDataBasesExceptionWhenIdIsDependent() {

    }

    @Test
    @Override
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

    }

    @Test
    @Override
    public void deleteShouldReturnNothingWhenIdExists() {

    }


    @Test
    void deposit() {

        TransactionResponseOperationDto result = transactionService.deposit(1L, FactoryTransactions.TRANSACTION_REQUEST_DTO);

        Assertions.assertNotNull(result);
    }

    @Test
    void withdraw() {
    }
}