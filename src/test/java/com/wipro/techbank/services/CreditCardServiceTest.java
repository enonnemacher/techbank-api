package com.wipro.techbank.services;

import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.dtos.CreditCardResponseDto;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
class CreditCardServiceTest {

    @InjectMocks
    private CreditCardService creditCardService;

    @Mock
    private CreditCardRepository creditCardRepository;

    private CreditCardRequestDto requestDto;
    private CreditCard entity;
    private PageImpl<CreditCard> page;
    private Long existsId;
    private Long nonExistsId;
    private Long dependentId;

    @BeforeEach
    void setUp() {
        existsId = 1L;
        nonExistsId = 2L;
        dependentId = 3L;
        requestDto = Factory.createCreditCardRequestDto();
        entity = Factory.createCreditCard();
        page = new PageImpl<>(Factory.createCreditCardList());

        Mockito.when(creditCardRepository.save(entity)).thenReturn(entity);

        Mockito.when(creditCardRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(creditCardRepository.findById(existsId)).thenReturn(Optional.of(entity));
        Mockito.when(creditCardRepository.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        doNothing().when(creditCardRepository).deleteById(existsId);
        doThrow(ResourceNotFoundException.class).when(creditCardRepository).deleteById(nonExistsId);
        doThrow(DataBasesException.class).when(creditCardRepository).deleteById(dependentId);
    }

    @Test
    public void findAllShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<CreditCardResponseDto> result = creditCardService.findAllPaged(pageable);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSize(), 10);
        verify(creditCardRepository, times(1)).findAll(pageable);
    }

    @Test
    void findByIdShouldReturnCreditCardResponseDtoWhenIdExixts() {
        CreditCardResponseDto result = creditCardService.findById(existsId);

        // Assert
        Assertions.assertNotNull(result);
        verify(creditCardRepository, times(1)).findById(existsId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            creditCardService.findById(nonExistsId);
        });

        // Assert
        verify(creditCardRepository, times(1)).findById(nonExistsId);
    }


    @Test
    void createShouldReturnCreditCardResponseDto() {

        // Act
        CreditCardResponseDto result = creditCardService.create(requestDto);

        // Assert
        Assertions.assertNotNull(result);
    }


    @Test
    void deleteShouldThrowDataBasesExceptionWhenIdIsDependent() {
        Assertions.assertThrows(DataBasesException.class, () -> {
            creditCardService.delete(dependentId);
        });
        verify(creditCardRepository, times(1)).deleteById(dependentId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            creditCardService.delete(nonExistsId);
        });
        verify(creditCardRepository, times(1)).deleteById(nonExistsId);
    }

    @Test
    void deleteShouldReturnNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            creditCardService.delete(existsId);
        });
        verify(creditCardRepository, times(1)).deleteById(existsId);
    }
}