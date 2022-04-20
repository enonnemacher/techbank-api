package com.wipro.techbank.controllers;



import com.wipro.techbank.dtos.SpecialAccountDto;
import com.wipro.techbank.services.SpecialAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/special-account")
public class SpecialAccountController {

    @Autowired
    private SpecialAccountService specialAccountService;

    @PostMapping
    public ResponseEntity<SpecialAccountDto> create(@RequestBody SpecialAccountDto specialAccountDto) {
        SpecialAccountDto creditCardResponseDto = specialAccountService.create(specialAccountDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(specialAccountDto.getId()).toUri();
        return ResponseEntity.created(uri).body(specialAccountDto);
    }

    @GetMapping
    public ResponseEntity<Page<SpecialAccountDto>> findAll(Pageable pageable) {
        Page<SpecialAccountDto> list = specialAccountService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SpecialAccountDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(specialAccountService.findById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specialAccountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
