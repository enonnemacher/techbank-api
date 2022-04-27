package com.wipro.techbank.services;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.CheckingAccountRequestDto;
import com.wipro.techbank.dtos.CheckingAccountResponseDto;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.tests.FactoryAccounts;
import com.wipro.techbank.tests.FactoryClient;
import com.wipro.techbank.tests.FactoryCreditCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wipro.techbank.tests.FactoryAccounts.CHECKING_ACCOUNT_GET_BY_ID_ENTITY;
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

    @Mock
    private ClientRepository clientRepository;

    @Mock
    CreditCardRepository creditCardRepository;

    private CheckingAccount checkingAccountEntity;

    private Client entityCient;
    private CreditCard entityCreditCard;
    private PageImpl<CheckingAccount> page;
    private CheckingAccountResponseDto checkingAccountDto;
    private CheckingAccountRequestDto checkingAccountRequestDto;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        checkingAccountEntity = FactoryAccounts.createCheckingAccount();

        entityCient = FactoryClient.CLIENT_ENTITY;

        entityCreditCard = FactoryCreditCard.CREDIT_CARD_TEST;

        page = new PageImpl<>(List.of(checkingAccountEntity));
        List<CheckingAccount> list = new ArrayList<>();
        list.add(checkingAccountEntity);

        checkingAccountDto = FactoryAccounts.createCheckingAccountDto();
        checkingAccountDto.setClientId(getExistsId());
        checkingAccountRequestDto = FactoryAccounts.createCheckingAccountRequestDto();

        Mockito.when(modelMapper.map(checkingAccountEntity, CheckingAccountResponseDto.class)).thenReturn(checkingAccountDto);
        Mockito.when(modelMapper.map(checkingAccountRequestDto, CheckingAccount.class)).thenReturn(checkingAccountEntity);

        Mockito.when(checkingAccountRepository.save(checkingAccountEntity)).thenReturn(checkingAccountEntity);

        Mockito.when(checkingAccountRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(checkingAccountRepository.findById(getExistsId())).thenReturn(Optional.of(checkingAccountEntity));
        Mockito.when(checkingAccountRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        Mockito.when(clientRepository.findById(getExistsId())).thenReturn(Optional.of(entityCient));
        Mockito.when(clientRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        Mockito.when(clientRepository.getById(getExistsId())).thenReturn(entityCient);
        Mockito.when(creditCardRepository.getById(getExistsId())).thenReturn(entityCreditCard);
        Mockito.when(checkingAccountRepository.getById(getExistsId())).thenReturn(CHECKING_ACCOUNT_GET_BY_ID_ENTITY);

        Mockito.when(creditCardRepository.findById(getExistsId())).thenReturn(Optional.of(entityCreditCard));
        Mockito.when(creditCardRepository.findById(getExistsId())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(checkingAccountRepository).deleteById(getExistsId());
        doThrow(ResourceNotFoundException.class).when(checkingAccountRepository).deleteById(getNonExistsId());
        doThrow(DataBasesException.class).when(checkingAccountRepository).deleteById(getDependentId());
    }
    @Test
    @Override
    public void findAllShouldReturnPage() {
        int expectedLength = 1;
        Pageable pageable = PageRequest.of(0, 10);
        // Act
        Page<CheckingAccountResponseDto> result = checkingAccountService.findAll(pageable);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSize(), expectedLength);
        verify(checkingAccountRepository, times(1)).findAll(pageable);
    }

    @Test
    @Override
    public void findByIdShouldReturnDtoWhenIdExixts() {
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
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            checkingAccountService.findById(getNonExistsId());
        });

        // Assert
        verify(checkingAccountRepository, times(1)).findById(getNonExistsId());

    }

    @Test
    @Override
    public void createShouldReturnDto() {
        // Act
        CheckingAccountResponseDto result = checkingAccountService.create(checkingAccountRequestDto);

        // Assert
        Assertions.assertNotNull(result);
    }

    @Test
    @Override
    public void deleteShouldThrowDataBasesExceptionWhenIdIsDependent() {
        Assertions.assertThrows(DataBasesException.class, () -> {
            checkingAccountService.remove(getDependentId());
        });
        verify(checkingAccountRepository, times(1)).deleteById(getDependentId());
    }

    @Test
    @Override
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            checkingAccountService.remove(getNonExistsId());
        });
        verify(checkingAccountRepository, times(1)).deleteById(getNonExistsId());
    }

    @Test
    @Override
    public void deleteShouldReturnNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            checkingAccountService.remove(getExistsId());
        });
        verify(checkingAccountRepository, times(1)).deleteById(getExistsId());

    }
}