package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.dtos.ClientDto;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.wipro.techbank.tests.FactoryClient.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    private Client entity;
    private PageImpl<Client> page;
    private ClientDto clientDto;

    private final String expectedCpf = "756.394.430-30";
    private final String expectedName = "Fulano Beltrano dos Testes";
    private final String expectedPhoneNumber = "(10) 91998-9673";
    private final String expectedEmail = "fulano.beltrano.testes@techbank.com";
    private Long existsId;
    private Long nonExistsId;
    private Long dependentId;

    @BeforeEach
    public void setUpClient() {
        existsId = 1L;
        nonExistsId = 2L;
        dependentId = 3L;
        entity = CLIENT_ENTITY;

        page = new PageImpl<>(CLIENTS_ENTITY_LIST);

        clientDto = CLIENT_DTO;

        when(clientRepository.save(entity)).thenReturn(entity);

        when(clientRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        when(clientRepository.findById(existsId)).thenReturn(Optional.of(entity));
        when(clientRepository.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        when(modelMapper.map(entity, ClientDto.class)).thenReturn(clientDto);
        when(modelMapper.map(clientDto, Client.class)).thenReturn(entity);

        when(clientRepository.getById(existsId)).thenReturn(entity);

        doNothing().when(clientRepository).deleteById(existsId);
        doThrow(ResourceNotFoundException.class).when(clientRepository).deleteById(nonExistsId);
        doThrow(DataBasesException.class).when(clientRepository).deleteById( dependentId);
    }

    @AfterEach
    public void afterEach() {
        entity = CLIENT_ENTITY;
    }

    @Test
    public void findAllShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Integer expectedSizeList = 10;

        // Act
        Page<ClientDto> result = clientService.findAll(pageable);

        // Assert
        Assertions.assertNotNull(result);

        Assertions.assertEquals(result.getSize(), expectedSizeList);
        verify(clientRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnDtoWhenIdExixts() {
        ClientDto result = clientService.findById(existsId);

        // Assert
        Assertions.assertNotNull(result);
        verify(clientRepository, times(1)).findById(existsId);
        Assertions.assertEquals(result.getCpf(), expectedCpf);
        Assertions.assertEquals(result.getName(), expectedName);
        Assertions.assertEquals(result.getPhoneNumber(), expectedPhoneNumber);
        Assertions.assertEquals(result.getEmail(), expectedEmail);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            clientService.findById(nonExistsId);
        });

        // Assert
        verify(clientRepository, times(1)).findById(nonExistsId);
    }

    @Test
    public void createShouldReturnDto() {
        // Act
        ClientDto result = clientService.save(clientDto);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCpf(), expectedCpf);
        Assertions.assertEquals(result.getName(), expectedName);
        Assertions.assertEquals(result.getPhoneNumber(), expectedPhoneNumber);
        Assertions.assertEquals(result.getEmail(), expectedEmail);
    }

    @Test
    public void deleteShouldThrowDataBasesExceptionWhenIdIsDependent() {
        Assertions.assertThrows(DataBasesException.class, () -> {
            clientService.remove( dependentId);
        });
        verify(clientRepository, times(1)).deleteById( dependentId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            clientService.remove(nonExistsId);
        });
        verify(clientRepository, times(1)).deleteById(nonExistsId);
    }

    @Test
    public void deleteShouldReturnNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            clientService.remove(existsId);
        });
        verify(clientRepository, times(1)).deleteById(existsId);
    }

//    @Test
//    public void updateShouldReturnDtoWhenIdExists() {
//        ClientDto clientUpdateDto = new ClientDto();
//        clientUpdateDto.setCpf("756.394.430-30");
//        clientUpdateDto.setName("Fulano Beltrano dos Testes UPDATE");
//        clientUpdateDto.setEmail("fulano.beltrano.testes.UPDATE@techbank.com");
//        clientUpdateDto.setPhoneNumber("(62) 91998-9673");
//        Client entityUpdate = entity;
//        entityUpdate.setId(1L);
//        entityUpdate.setCpf("756.394.430-30");
//        entityUpdate.setName("Fulano Beltrano dos Testes UPDATE");
//        entityUpdate.setEmail("fulano.beltrano.testes.UPDATE@techbank.com");
//        entityUpdate.setPhoneNumber("(62) 91998-9673");
//
//
//        when(clientRepository.getById(existsId)).thenReturn(entityUpdate);
//        when(clientRepository.save(entity)).thenReturn(entityUpdate);
//
//        // Act
//        ClientDto result = clientService.update(existsId, clientUpdateDto);
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.getCpf(), "756.394.430-30");
////        Assertions.assertEquals(result.getName(), "Fulano Beltrano dos Testes UPDATE");
//        Assertions.assertEquals(result.getPhoneNumber(), "(62) 91998-9673");
//        Assertions.assertEquals(result.getEmail(), "fulano.beltrano.testes.UPDATE@techbank.com");
//        // Assertions.assertEquals(result.getId(), existsId);
//    }
}