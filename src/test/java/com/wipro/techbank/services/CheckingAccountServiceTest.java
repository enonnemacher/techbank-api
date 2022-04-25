package com.wipro.techbank.services;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.dtos.CheckingAccountResponseDto;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)

class CheckingAccountServiceTest extends TestsServiceAbstract{


    @InjectMocks
    private CheckingAccountService checkingAccountService;

    @Mock
    private ModelMapper modelMapper;

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
        List<CheckingAccount> list = new ArrayList<>();
        list.add(entity);

        checkingAccountDto = Factory.createCheckingAccountDto();

        Mockito.when(modelMapper.map(entity, CheckingAccountResponseDto.class)).thenReturn(checkingAccountDto);
        Mockito.when(checkingAccountRepository.save(entity)).thenReturn(entity);

        Mockito.when(checkingAccountRepository.findAll()).thenReturn(list);

        Mockito.when(checkingAccountRepository.findById(getExistsId())).thenReturn(Optional.of(entity));
        Mockito.when(checkingAccountRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(checkingAccountRepository).deleteById(getExistsId());
        doThrow(ResourceNotFoundException.class).when(checkingAccountRepository).deleteById(getNonExistsId());
        doThrow(DataBasesException.class).when(checkingAccountRepository).deleteById(getDependentId());
    }
    @Test
    @Override
    public void findAllShouldReturnPage() {
        int expectedLength = 1;
        // Act
        List<CheckingAccountResponseDto> result = checkingAccountService.findAll();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), expectedLength);
        verify(checkingAccountRepository, times(1)).findAll();
    }

    @Test
    @Override
    public void findByIdShouldReturnCreditCardResponseDtoWhenIdExixts() {
        CheckingAccountResponseDto result = checkingAccountService.findById(getExistsId());
        Double expectedBalance = 1500.00;
        String expectedCreditCardNumber = "4527 0144 9327 6163";
        Double expectedCreditCardLimit = 500.00;
        Long expectedClientId = 1L;
        String expectedClientName = "Fulano Beltrano dos Testes";

        // Assert
        Assertions.assertNotNull(result);
        verify(checkingAccountRepository, times(1)).findById(getExistsId());
        Assertions.assertEquals(result.getBalance(), expectedBalance);
        Assertions.assertEquals(result.getCreditCardCardNumber(), expectedCreditCardNumber);
        Assertions.assertEquals(result.getCreditCardLimitCredit(), expectedCreditCardLimit);
        Assertions.assertEquals(result.getClientId(), expectedClientId);
        Assertions.assertEquals(result.getClientName(), expectedClientName);
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