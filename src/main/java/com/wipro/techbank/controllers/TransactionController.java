package com.wipro.techbank.controllers;

import com.wipro.techbank.dtos.TransactionDto;
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
    public ResponseEntity<Page<TransactionDto>> findAll(Pageable pageable) {
        Page<TransactionDto> list = transactionService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }
}
