package com.wipro.techbank.services;

import com.wipro.techbank.domain.AccountType;
import com.wipro.techbank.dtos.TransactionRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest extends TestsServiceAbstract{


    @BeforeEach
    public void setUp() {
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
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setValue(200.00);
        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
    }

    @Test
    void withdraw() {
    }
}