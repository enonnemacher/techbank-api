package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.dtos.ClientDto;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
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

    public ClientDto save(ClientDto clientDto) {
        Client entity = new Client();
        copyDtoToEntity(clientDto, entity);
        entity = clientRepository.save(entity);
        return new ClientDto(entity);
    }

    public Page<ClientDto> findAll(Pageable pageable) {
        Page<Client> clientsList = clientRepository.findAll(pageable);
        return clientsList.map(ClientDto::new);
    }

    public ClientDto findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada."));
        return new ClientDto(entity);
    }

    public ClientDto update(Long id, ClientDto clientDto) {
        Client entity = clientRepository.getById(id);
        copyDtoToEntity(clientDto, entity);
        entity = clientRepository.save(entity);
        return new ClientDto(entity);
    }

    public void remove(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id " + id + " não encontrado.");
        } catch (DataIntegrityViolationException e){
            throw new DataBasesException("Violação de integridade");
        }
    }

    private void copyDtoToEntity(ClientDto clientDto, Client client) {
//        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setCpf(clientDto.getCpf());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setEmail(clientDto.getEmail());
    }
}