package com.wipro.techbank.services;


import com.wipro.techbank.domain.*;
import com.wipro.techbank.dtos.TransactionRequestDto;
import com.wipro.techbank.dtos.TransactionResponseDto;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.repositories.SpecialAccountRepository;
import com.wipro.techbank.repositories.TransactionRepository;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private SpecialAccountRepository specialAccountRepository;

    public Page<TransactionResponseDto> findAllPaged(Pageable pageable) {
        Page<Transaction> operations = transactionRepository.findAll(pageable);
        return operations.map(TransactionResponseDto::new);
    }

    @Transactional(readOnly = true)
    public TransactionResponseDto findById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        Transaction entity = transaction.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new TransactionResponseDto(entity);
    }

    public TransactionResponseDto deposit(Long accountId, TransactionRequestDto transactionDto) {
        Account account = null;
        Transaction transaction = new Transaction();

        transaction.setOperation(Operation.DEPOSIT);
        transaction.setValue(transactionDto.getValue());
        transaction.setAccountType(transactionDto.getAccountType());

        if (transactionDto.getAccountType().equals(AccountType.CHECKING_ACCOUNT)) {
            account = checkingAccountRepository.getById(accountId);
            account.deposit(transactionDto.getValue());
            transaction.setAccount(account);
            transaction = transactionRepository.save(transaction);
        }
        if (transactionDto.getAccountType().equals(AccountType.ESPECIAL_ACCOUNT)) {
            account = specialAccountRepository.getById(accountId);
            account.deposit(transactionDto.getValue());
            transaction.setAccount(account);
            transaction = transactionRepository.save(transaction);
        }
        return new TransactionResponseDto(transaction, account.getBalance());
    }

    private void copyDtoToEntity(TransactionResponseDto dto, Transaction entity) {
        entity.setOperation(dto.getOperation());
        entity.setValue(dto.getValue());
    }

}
