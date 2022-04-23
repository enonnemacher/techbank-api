package com.wipro.techbank.controllers;

import com.wipro.techbank.dtos.TransactionRequestDto;
import com.wipro.techbank.dtos.TransactionResponseDto;
import com.wipro.techbank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDto>> findAll(Pageable pageable) {
        Page<TransactionResponseDto> list = transactionService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @PostMapping("/deposits/{id}")
    public ResponseEntity<TransactionResponseDto> deposit(@PathVariable Long id, @RequestBody TransactionRequestDto dto){
        TransactionResponseDto transactionResponseDto = transactionService.deposit(id, dto);
        return ResponseEntity.ok().body(transactionResponseDto);
    }
}
