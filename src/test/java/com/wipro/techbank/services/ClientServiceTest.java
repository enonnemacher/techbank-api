package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.dtos.ClientDto;
import com.wipro.techbank.repositories.ClientRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class ClientServiceTest extends TestsServiceAbstract{

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client entity;
    private PageImpl<Client> page;
    ClientDto clientDto;
    private final String expectedCpf = "756.394.430-30";
    private final String expectedName = "Fulano Beltrano dos Testes";
    private final String expectedPhoneNumber = "(10) 91998-9673";
    private final String expectedEmail = "fulano.beltrano.testes@techbank.com";

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        entity = Factory.createClient();

        page = new PageImpl<>(Factory.createClientList());

        clientDto = Factory.createClientDto();

        Mockito.when(clientRepository.save(entity)).thenReturn(entity);

        Mockito.when(clientRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(clientRepository.findById(getExistsId())).thenReturn(Optional.of(entity));
        Mockito.when(clientRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(clientRepository).deleteById(getExistsId());
        doThrow(ResourceNotFoundException.class).when(clientRepository).deleteById(getNonExistsId());
        doThrow(DataBasesException.class).when(clientRepository).deleteById(getDependentId());
    }

    @Test
    @Override
    public void findAllShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<ClientDto> result = clientService.findAll(pageable);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSize(), getSizeLists());
        verify(clientRepository, times(1)).findAll(pageable);
    }

    @Test
    @Override
    public void findByIdShouldReturnCreditCardResponseDtoWhenIdExixts() {
        ClientDto result = clientService.findById(getExistsId());

        // Assert
        Assertions.assertNotNull(result);
        verify(clientRepository, times(1)).findById(getExistsId());
        Assertions.assertEquals(result.getCpf(), expectedCpf);
        Assertions.assertEquals(result.getName(), expectedName);
        Assertions.assertEquals(result.getPhoneNumber(), expectedPhoneNumber);
        Assertions.assertEquals(result.getEmail(), expectedEmail);
    }

    @Test
    @Override
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            clientService.findById(getNonExistsId());
        });

        // Assert
        verify(clientRepository, times(1)).findById(getNonExistsId());
    }

    @Test
    @Override
    public void createShouldReturnCreditCardResponseDto() {
        // Act
        ClientDto result = clientService.save(clientDto);

        // Assert
        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.getCpf(), expectedCpf);
//        Assertions.assertEquals(result.getName(), expectedName);
//        Assertions.assertEquals(result.getPhoneNumber(), expectedPhoneNumber);
//        Assertions.assertEquals(result.getEmail(), expectedEmail);
    }

    @Test
    @Override
    public void deleteShouldThrowDataBasesExceptionWhenIdIsDependent() {
        Assertions.assertThrows(DataBasesException.class, () -> {
            clientService.remove(getDependentId());
        });
        verify(clientRepository, times(1)).deleteById(getDependentId());
    }

    @Test
    @Override
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            clientService.remove(getNonExistsId());
        });
        verify(clientRepository, times(1)).deleteById(getNonExistsId());
    }

    @Test
    @Override
    public void deleteShouldReturnNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            clientService.remove(getExistsId());
        });
        verify(clientRepository, times(1)).deleteById(getExistsId());
    }
}