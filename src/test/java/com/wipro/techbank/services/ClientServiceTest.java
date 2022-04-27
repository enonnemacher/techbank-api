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
import org.springframework.beans.factory.annotation.Autowired;
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
class ClientServiceTest extends TestsServiceAbstract{

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


    @BeforeEach
    public void setUpClient() {
        super.setUp();
        entity = CLIENT_ENTITY;

        page = new PageImpl<>(CLIENTS_ENTITY_LIST);

        clientDto = CLIENT_DTO;

        when(clientRepository.save(entity)).thenReturn(entity);

        when(clientRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        when(clientRepository.findById(getExistsId())).thenReturn(Optional.of(entity));
        when(clientRepository.findById(getNonExistsId())).thenThrow(ResourceNotFoundException.class);

        when(modelMapper.map(entity, ClientDto.class)).thenReturn(clientDto);
        when(modelMapper.map(clientDto, Client.class)).thenReturn(entity);

        when(clientRepository.getById(getExistsId())).thenReturn(entity);

        doNothing().when(clientRepository).deleteById(getExistsId());
        doThrow(ResourceNotFoundException.class).when(clientRepository).deleteById(getNonExistsId());
        doThrow(DataBasesException.class).when(clientRepository).deleteById(getDependentId());
    }

    @AfterEach
    public void afterEach() {
        entity = CLIENT_ENTITY;
    }

    @Test
    @Override
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
    @Override
    public void findByIdShouldReturnDtoWhenIdExixts() {
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
    public void createShouldReturnDto() {
        // Act
//        clientDto.setId(null);
        ClientDto result = clientService.save(clientDto);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCpf(), expectedCpf);
        Assertions.assertEquals(result.getName(), expectedName);
        Assertions.assertEquals(result.getPhoneNumber(), expectedPhoneNumber);
        Assertions.assertEquals(result.getEmail(), expectedEmail);
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
//        when(clientRepository.getById(getExistsId())).thenReturn(entityUpdate);
//        when(clientRepository.save(entity)).thenReturn(entityUpdate);
//
//        // Act
//        ClientDto result = clientService.update(getExistsId(), clientUpdateDto);
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.getCpf(), "756.394.430-30");
////        Assertions.assertEquals(result.getName(), "Fulano Beltrano dos Testes UPDATE");
//        Assertions.assertEquals(result.getPhoneNumber(), "(62) 91998-9673");
//        Assertions.assertEquals(result.getEmail(), "fulano.beltrano.testes.UPDATE@techbank.com");
//        // Assertions.assertEquals(result.getId(), getExistsId());
//    }
}