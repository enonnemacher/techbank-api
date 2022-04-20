package com.wipro.techbank.controllers;

import com.wipro.techbank.dtos.CheckingAccountDto;
import com.wipro.techbank.services.CheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/checking-account")
public class CheckingAccountController {

    @Autowired
    private CheckingAccountService checkingAccountService;

    @PostMapping
    public ResponseEntity<CheckingAccountDto> create(@RequestBody CheckingAccountDto checkingAccountDto) {
        CheckingAccountDto checkingAccountResponse = checkingAccountService.create(checkingAccountDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(checkingAccountDto.getId()).toUri();
        return ResponseEntity.created(uri).body(checkingAccountDto);
    }

    @GetMapping
    public ResponseEntity<Page<CheckingAccountDto>> findAll(Pageable pageable) {
        Page<CheckingAccountDto> list = checkingAccountService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CheckingAccountDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(checkingAccountService.findById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        checkingAccountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
