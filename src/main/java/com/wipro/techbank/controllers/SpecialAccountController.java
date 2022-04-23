package com.wipro.techbank.controllers;

import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.dtos.SpecialAccountRequestDto;
import com.wipro.techbank.dtos.SpecialAccountResponseDto;
import com.wipro.techbank.repositories.SpecialAccountRepository;
import com.wipro.techbank.services.SpecialAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/special-accounts")
public class SpecialAccountController {

    @Autowired
    private SpecialAccountService specialAccountService;

    @Autowired
    private SpecialAccountRepository specialAccountRepository;

//    @Autowired
//    private TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SpecialAccountRequestDto specialAccountRequestDto){
        specialAccountService.create(specialAccountRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialAccountResponseDto> specialAccountById(@PathVariable Long id){
        return ResponseEntity.ok(specialAccountService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialAccountResponseDto> updateSpecialAccount(
            @PathVariable Long id, @RequestBody SpecialAccountRequestDto specialAccountRequestDto) {
        return ResponseEntity.ok().body(specialAccountService
                .updateSpecialAccount(id, specialAccountRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<SpecialAccountResponseDto>> specialAccountsFindAll(){
        return ResponseEntity.ok(specialAccountService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> specialAccountDelete(@PathVariable Long id){
        System.out.println("ID: " + id);
        specialAccountService.remove(id);
        return  ResponseEntity.noContent().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdraw(@RequestParam Long id, @RequestParam Double value) {
        Optional<SpecialAccount> specialAccount = specialAccountRepository.findById(id);

        if (specialAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            specialAccountService.withdraw(id, value);
            return ResponseEntity.ok().body(String.format("Saldo Atual: R$ %.2f%n Credito usado: R$ %.2f", specialAccount.get().getBalance(), specialAccount.get().getCreditSpecialUsed()));
        }
    }
}
