package com.wipro.techbank.services;


import com.wipro.techbank.domain.Transaction;
import com.wipro.techbank.dtos.TransactionDto;
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

    public Page<TransactionDto> findAllPaged(Pageable pageable) {
        Page<Transaction> operations = transactionRepository.findAll(pageable);
        return operations.map(TransactionDto::new);
    }

    @Transactional(readOnly = true)
    public TransactionDto findById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        Transaction entity = transaction.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new TransactionDto(entity);
    }

    private void copyDtoToEntity(TransactionDto dto, Transaction entity) {
        entity.setOperation(dto.getOperation());
        entity.setValue(dto.getValue());
    }

}
