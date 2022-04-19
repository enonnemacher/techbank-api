package com.wipro.techbank.controller;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody Client client) {
        return clientService.save(client);
    }

    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Client findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PutMapping(path = "{/id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @DeleteMapping(path = "{/id}")
    public void removeClient(@PathVariable Long id) {
        clientService.remove(id);
    }
}
