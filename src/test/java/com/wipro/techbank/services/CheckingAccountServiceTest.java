package com.wipro.techbank.services;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.dtos.CheckingAccountResponseDto;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
class CheckingAccountServiceTest extends TestsServiceAbstract{


    @InjectMocks
    private CheckingAccountService checkingAccountService;

    @Mock
    private CheckingAccountRepository checkingAccountRepository;

    private CheckingAccount entity;
    private PageImpl<CheckingAccount> page;
    CheckingAccountResponseDto checkingAccountDto;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        entity = Factory.createCheckingAccount();
        page = new PageImpl<>(List.of(entity));

        checkingAccountDto = Factory.createCheckingAccountDto();

        Mockito.when(checkingAccountRepository.save(entity)).thenReturn(entity);

        Mockito.when(checkingAccountRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(checkingAccountRepository.findById(getExistsId())).thenReturn(Optional.of(entity));
        Mockito.when(checkingAccountRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(checkingAccountRepository).deleteById(getExistsId());
        doThrow(ResourceNotFoundException.class).when(checkingAccountRepository).deleteById(getNonExistsId());
        doThrow(DataBasesException.class).when(checkingAccountRepository).deleteById(getDependentId());
    }
    @Test
    @Override
    public void findAllShouldReturnPage() {

    }

    @Test
    @Override
    public void findByIdShouldReturnCreditCardResponseDtoWhenIdExixts() {

    }

    @Test
    @Override
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {

    }

    @Override
    public void createShouldReturnCreditCardResponseDto() {

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
}