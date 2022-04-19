package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public List<Client> findAll(){
        List<Client> clientsList = clientRepository.findAll();
        return clientsList;
    }

    public Client findById(Long id){
        //return clientRepository.findById(id).orElseThrow() -> NotFoundException("client");
    }

    public Client update(Long id, Client client){
        Client findClient = findById(id);
        //efetuar validação da busca
        return  clientRepository.save(client);
    }

    public void remove(Long id){
        Client client = findById(id);
        // efetuar validação
        /*
        if (clientRepository.findById(id)){
            throw new NotFoundException();
        }*/
        clientRepository.deleteById(id);
    }
}
