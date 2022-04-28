package com.wipro.techbank.controllers;

import com.wipro.techbank.domain.Account;
import com.wipro.techbank.dtos.*;
import com.wipro.techbank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDto>> findAll(@ApiIgnore Pageable pageable) {
        Page<TransactionResponseDto> list = transactionService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @PostMapping("/deposits/{id}")
    public ResponseEntity<TransactionResponseOperationDto> deposit(@PathVariable Long id, @RequestBody TransactionRequestDto dto){
        TransactionResponseOperationDto transactionResponseDto = transactionService.deposit(id, dto);
        return ResponseEntity.ok().body(transactionResponseDto);
    }

    @PostMapping("/withdrawals/{id}")
    public ResponseEntity<TransactionResponseOperationDto> withdraw(@PathVariable Long id, @RequestBody TransactionRequestDto dto){
        TransactionResponseOperationDto transactionResponseDto = transactionService.withdraw(id, dto);
        return ResponseEntity.ok().body(transactionResponseDto);
    }

    @GetMapping(value = "/accounts/{id}/extracts")
    public ResponseEntity<List<TransactionResponseExtractDto>> extract(@PathVariable Long id) {
        return ResponseEntity.ok().body(transactionService.extract(id));
    }
}
