package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.dtos.ClientDto;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ClientDto save(ClientDto clientDto) {
        Client client = copyDtoToEntity(clientDto);
        clientRepository.save(client);
        return copyEntityToDTo(client);
    }

    public Page<ClientDto> findAll(Pageable pageable) {
        Page<Client> clientsList = clientRepository.findAll(pageable);
        return clientsList.map(this::copyEntityToDTo);
    }

    public ClientDto findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada."));
        return copyEntityToDTo(client.get());
    }

    public ClientDto update(Long id, ClientDto clientDto) {
        Client entity = clientRepository.getById(id);
        Client client = new Client();
        BeanUtils.copyProperties(copyDtoToEntity(clientDto), client);
        client.setId(id);
        BeanUtils.copyProperties(client, entity);
        clientRepository.save(entity);
        return copyEntityToDTo(entity);
    }

    public void remove(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado.");
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Violação de integridade");
        }
    }

    private Client copyDtoToEntity(ClientDto clientDto) {
        return modelMapper.map(clientDto, Client.class);
    }

    private ClientDto copyEntityToDTo(Client entity) {
        return modelMapper.map(entity, ClientDto.class);
    }
}