package com.wipro.techbank.controllers;

import com.wipro.techbank.dtos.SpecialAccountRequestDto;
import com.wipro.techbank.dtos.SpecialAccountResponseDto;
import com.wipro.techbank.services.SpecialAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/special-accounts")
public class SpecialAccountController {

    @Autowired
    private SpecialAccountService specialAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SpecialAccountResponseDto> create(@RequestBody SpecialAccountRequestDto specialAccountRequestDto) {
        return ResponseEntity.ok(specialAccountService.create(specialAccountRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialAccountResponseDto> specialAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(specialAccountService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialAccountResponseDto> updateSpecialAccount(
            @PathVariable Long id, @RequestBody SpecialAccountRequestDto specialAccountRequestDto) {
        return ResponseEntity.ok().body(specialAccountService
                .updateSpecialAccount(id, specialAccountRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<SpecialAccountResponseDto>> specialAccountsFindAll() {
        return ResponseEntity.ok(specialAccountService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> specialAccountDelete(@PathVariable Long id) {
        System.out.println("ID: " + id);
        specialAccountService.remove(id);
        return ResponseEntity.noContent().build();
    }
}