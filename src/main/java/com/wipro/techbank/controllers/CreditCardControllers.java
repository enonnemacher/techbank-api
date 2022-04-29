package com.wipro.techbank.controllers;

import com.wipro.techbank.dtos.CreditCardResponseDto;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.services.CreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

@RestController
@RequestMapping("/credit-cards")
@Api(value = "Credit Card", tags = "Credit Card")
public class CreditCardControllers {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping
    @ApiOperation(value = "Create Credit Card", tags = "Credit Card")
    public ResponseEntity<CreditCardResponseDto> create(@RequestBody CreditCardRequestDto creditCardRequestDto) {
        CreditCardResponseDto creditCardResponseDto = creditCardService.create(creditCardRequestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(creditCardResponseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(creditCardResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<CreditCardResponseDto>> findAll(@ApiIgnore Pageable pageable) {
        Page<CreditCardResponseDto> list = creditCardService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CreditCardResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(creditCardService.findById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        creditCardService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
