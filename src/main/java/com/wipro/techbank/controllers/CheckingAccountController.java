package com.wipro.techbank.controllers;

import com.wipro.techbank.dtos.CheckingAccountRequestDto;
import com.wipro.techbank.dtos.CheckingAccountResponseDto;
import com.wipro.techbank.services.CheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checking-accounts")
public class CheckingAccountController {
    @Autowired
    private CheckingAccountService checkingAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckingAccountResponseDto create(@RequestBody CheckingAccountRequestDto checkingAccount){
        return checkingAccountService.create(checkingAccount);
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
    public ResponseEntity<List<CheckingAccountResponseDto>> checkingAccountsFindAll(){
        return ResponseEntity.ok(checkingAccountService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> checkingAccountDelete(@PathVariable Long id) {
        checkingAccountService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
