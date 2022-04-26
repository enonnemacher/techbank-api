package com.wipro.techbank.services;

import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.dtos.CreditCardResponseDto;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.tests.Factory;
import com.wipro.techbank.tests.FactoryCreditCard;
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

import java.util.Optional;

import static com.wipro.techbank.tests.FactoryCreditCard.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
class CreditCardServiceTest extends TestsServiceAbstract{

    @InjectMocks
    private CreditCardService creditCardService;
    @Mock
    private CreditCardRepository creditCardRepository;
    private CreditCardRequestDto requestDto;
    private CreditCard entity;
    private PageImpl<CreditCard> page;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        requestDto = CREDIT_CARD_REQUEST_DTO_TEST;
        entity = CREDIT_CARD_TEST;
        page = new PageImpl<>(CREDIT_CARDS_LIST_TEST);

        Mockito.when(creditCardRepository.save(entity)).thenReturn(entity);

        Mockito.when(creditCardRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(creditCardRepository.findById(getExistsId())).thenReturn(Optional.of(entity));
        Mockito.when(creditCardRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(creditCardRepository).deleteById(getExistsId());
        doThrow(ResourceNotFoundException.class).when(creditCardRepository).deleteById(getNonExistsId());
        doThrow(DataBasesException.class).when(creditCardRepository).deleteById(getDependentId());
    }

    @Test
    @Override
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
    @Override
    public void findByIdShouldReturnDtoWhenIdExixts() {
        CreditCardResponseDto result = creditCardService.findById(getExistsId());

        // Assert
        Assertions.assertNotNull(result);
        verify(creditCardRepository, times(1)).findById(getExistsId());
    }

    @Test
    @Override
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            creditCardService.findById(getNonExistsId());
        });

        // Assert
        verify(creditCardRepository, times(1)).findById(getNonExistsId());
    }

    @Test
    @Override
    public void createShouldReturnDto() {

        // Act
        CreditCardResponseDto result = creditCardService.create(requestDto);

        // Assert
        Assertions.assertNotNull(result);
    }


    @Test
    @Override
    public void deleteShouldThrowDataBasesExceptionWhenIdIsDependent() {
        Assertions.assertThrows(DataBasesException.class, () -> {
            creditCardService.delete(getDependentId());
        });
        verify(creditCardRepository, times(1)).deleteById(getDependentId());
    }

    @Test
    @Override
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            creditCardService.delete(getNonExistsId());
        });
        verify(creditCardRepository, times(1)).deleteById(getNonExistsId());
    }

    @Test
    @Override
    public void deleteShouldReturnNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            creditCardService.delete(getExistsId());
        });
        verify(creditCardRepository, times(1)).deleteById(getExistsId());
    }
}