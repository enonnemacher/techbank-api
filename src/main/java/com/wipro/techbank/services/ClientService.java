package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.dtos.ClientDto;
import com.wipro.techbank.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Client> findAll() {
        List<Client> clientsList = clientRepository.findAll();
        return clientsList;
    }

    public Client findById(Long id) {
        //return clientRepository.findById(id).orElseThrow() -> NotFoundException("client");
        return null;
    }

    public Client update(Long id, Client client) {
        Client findClient = findById(id);
        //efetuar validação da busca
        return clientRepository.save(client);
    }

    public void remove(Long id) {
        Client client = findById(id);
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


