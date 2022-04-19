package com.wipro.techbank.controllers;

import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.services.SpecialAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialaccount")
public class SpecialAccountController {

    @Autowired
    private SpecialAccountService specialAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialAccount save(@RequestBody SpecialAccount specialAccount){
        return specialAccountService.save(specialAccount);
    }

    @GetMapping
    public List<SpecialAccount> findAll(){
        return specialAccountService.findAll();
    }

    @GetMapping("/{number}")
    public ResponseEntity<SpecialAccount> findByNumber(@PathVariable Long number){
        return ResponseEntity.ok().body(specialAccountService.findByNumber(number));
    }

    @PutMapping("/{number}")
    public ResponseEntity<SpecialAccount> updateSpecialAcocunt(@PathVariable Long number, @RequestBody SpecialAccount specialAccount){
        return ResponseEntity.ok().body(specialAccountService.update(number, specialAccount));
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> delete(@PathVariable Long number){
        specialAccountService.delete(number);
        return ResponseEntity.noContent().build();
    }
}
