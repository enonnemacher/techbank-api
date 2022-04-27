package com.wipro.techbank.controllers;

import com.wipro.techbank.dtos.CheckingAccountRequestDto;
import com.wipro.techbank.dtos.CheckingAccountResponseDto;
import com.wipro.techbank.services.CheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/checking-accounts")
public class CheckingAccountController {
    @Autowired
    private CheckingAccountService checkingAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CheckingAccountResponseDto> create(@RequestBody CheckingAccountRequestDto checkingAccount) {
        CheckingAccountResponseDto checkingAccountResponseDto = checkingAccountService.create(checkingAccount);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(checkingAccountResponseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(checkingAccountResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckingAccountResponseDto> checkingAccountFindById(@PathVariable Long id){
        return ResponseEntity.ok(checkingAccountService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckingAccountResponseDto> updateCheckingAccount(
            @PathVariable Long id, @RequestBody CheckingAccountRequestDto checkingAccountRequestDto){
        return ResponseEntity.ok().body(checkingAccountService
                .updateCheckingAccount(id, checkingAccountRequestDto));
    }

    @GetMapping
    public ResponseEntity<Page<CheckingAccountResponseDto>> checkingAccountsFindAll(@ApiIgnore Pageable pageable){
        Page<CheckingAccountResponseDto> checkingAccountResponseDtos = checkingAccountService.findAll(pageable);
        return ResponseEntity.ok(checkingAccountResponseDtos);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> checkingAccountDelete(@PathVariable Long id) {
        checkingAccountService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
