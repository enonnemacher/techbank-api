package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        List<Client> clientsList = clientRepository.findAll();
        return clientsList;
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
    }

    public Client update(Long id, Client client) {
        Client findClient = findById(id);
        return clientRepository.save(client);
    }

    public void remove(Long id) {
        Client client = findById(id);
        clientRepository.deleteById(id);
    }
}
