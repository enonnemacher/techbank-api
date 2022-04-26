package com.wipro.techbank.services;

import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.SpecialAccountRequestDto;
import com.wipro.techbank.dtos.SpecialAccountResponseDto;
import com.wipro.techbank.repositories.SpecialAccountRepository;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.tests.Factory;
import com.wipro.techbank.tests.FactoryClient;
import com.wipro.techbank.tests.FactoryCreditCard;
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

import static com.wipro.techbank.tests.FactoryClient.CLIENT_TEST;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SpecialAccountServiceTest extends TestsServiceAbstract{


    @InjectMocks
    private SpecialAccountService specialAccountService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SpecialAccountRepository specialAccountRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    CreditCardRepository creditCardRepository;

    private SpecialAccount specialAccountEntity;

    private Client entityCient;
    private CreditCard entityCreditCard;
    private PageImpl<SpecialAccount> page;
    private SpecialAccountResponseDto specialAccountDto;
    private SpecialAccountRequestDto specialAccountRequestDto;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        specialAccountEntity = Factory.createSpecialAccount();

        entityCient = CLIENT_TEST;

        entityCreditCard = FactoryCreditCard.CREDIT_CARD_TEST;

        page = new PageImpl<>(List.of(specialAccountEntity));
        List<SpecialAccount> list = new ArrayList<>();
        list.add(specialAccountEntity);

        specialAccountDto = Factory.createSpecialAccountResponseDto();
        specialAccountDto.setClientId(getExistsId());
        specialAccountRequestDto = Factory.createSpecialRequestAccount();

        when(modelMapper.map(specialAccountEntity, SpecialAccountResponseDto.class)).thenReturn(specialAccountDto);
        when(modelMapper.map(specialAccountRequestDto, SpecialAccount.class)).thenReturn(specialAccountEntity);

        when(specialAccountRepository.save(specialAccountEntity)).thenReturn(specialAccountEntity);

        when(specialAccountRepository.findAll()).thenReturn(list);

        when(specialAccountRepository.findById(getExistsId())).thenReturn(Optional.of(specialAccountEntity));
        when(specialAccountRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        when(clientRepository.findById(getExistsId())).thenReturn(Optional.of(entityCient));
        when(clientRepository.getById(getExistsId())).thenReturn(entityCient);
        when(clientRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        when(creditCardRepository.findById(getExistsId())).thenReturn(Optional.of(entityCreditCard));
        when(creditCardRepository.findById(getExistsId())).thenThrow(ResourceNotFoundException.class);
        when(creditCardRepository.getById(getExistsId())).thenReturn(entityCreditCard);

        doNothing().when(specialAccountRepository).deleteById(getExistsId());
        doThrow(ResourceNotFoundException.class).when(specialAccountRepository).deleteById(getNonExistsId());
        doThrow(DataBasesException.class).when(specialAccountRepository).deleteById(getDependentId());
    }
    @Test
    @Override
    public void findAllShouldReturnPage() {
        int expectedLength = 1;
        // Act
        List<SpecialAccountResponseDto> result = specialAccountService.findAll();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), expectedLength);
        verify(specialAccountRepository, times(1)).findAll();
    }

    @Test
    @Override
    public void findByIdShouldReturnDtoWhenIdExixts() {
        SpecialAccountResponseDto result = specialAccountService.findById(getExistsId());
        Double expectedBalance = 1500.00;
        String expectedCreditCardNumber = "4527 0144 9327 6163";
        Double expectedCreditCardLimit = 500.00;
        Long expectedClientId = 1L;
        String expectedClientName = "Fulano Beltrano dos Testes";

        // Assert
        Assertions.assertNotNull(result);
        verify(specialAccountRepository, times(1)).findById(getExistsId());
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
            specialAccountService.findById(getNonExistsId());
        });

        // Assert
        verify(specialAccountRepository, times(1)).findById(getNonExistsId());

    }

    @Test
    @Override
    public void createShouldReturnDto() {
        // Act
        SpecialAccountResponseDto result = specialAccountService.create(specialAccountRequestDto);

        // Assert
        Assertions.assertNotNull(result);
    }

    @Test
    @Override
    public void deleteShouldThrowDataBasesExceptionWhenIdIsDependent() {
        Assertions.assertThrows(DataBasesException.class, () -> {
            specialAccountService.remove(getDependentId());
        });
        verify(specialAccountRepository, times(1)).deleteById(getDependentId());
    }

    @Test
    @Override
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            specialAccountService.remove(getNonExistsId());
        });
        verify(specialAccountRepository, times(1)).deleteById(getNonExistsId());
    }

    @Test
    @Override
    public void deleteShouldReturnNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            specialAccountService.remove(getExistsId());
        });
        verify(specialAccountRepository, times(1)).deleteById(getExistsId());
    }
}