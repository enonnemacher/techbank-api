package com.wipro.techbank.controllers;

import com.wipro.techbank.dtos.CreditCardResponseDto;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardControllers {
    
    @Autowired
    private CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<CreditCardResponseDto> create(@RequestBody CreditCardRequestDto creditCardRequestDto) {
        CreditCardResponseDto creditCardResponseDto = creditCardService.create(creditCardRequestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(creditCardResponseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(creditCardResponseDto);
    }
    
}
