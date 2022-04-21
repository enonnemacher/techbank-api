package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.dtos.ClientDto;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Client update(Long id, Client client) {
        //Client findClient = findById(id);
        //efetuar validação da busca
        return clientRepository.save(client);
    }

    public void remove(Long id) {
        //Client client = findById(id);
        // efetuar validação
        /*
        if (clientRepository.findById(id)){
            throw new NotFoundException();
        }*/
        clientRepository.deleteById(id);
    }

    private void copyDtoToEntity(ClientDto clientDto, Client client) {
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setCpf(clientDto.getCpf());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setEmail(clientDto.getEmail());
    }
}


