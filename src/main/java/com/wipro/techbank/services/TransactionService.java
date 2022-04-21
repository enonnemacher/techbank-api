package com.wipro.techbank.services;


import com.wipro.techbank.domain.Transaction;
import com.wipro.techbank.dtos.TransactionRequestDto;
import com.wipro.techbank.dtos.TransactionResponseDto;
import com.wipro.techbank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionResponseDto create(TransactionRequestDto dto) {
        Transaction entity = new Transaction();
        copyDtoToEntity(dto, entity);
        entity = transactionRepository.save(entity);
        return new TransactionResponseDto(entity);
    }

    public Page<TransactionResponseDto> findAllPaged(Pageable pageable) {
        Page<Transaction> operations = transactionRepository.findAll(pageable);
        return operations.map(TransactionResponseDto::new);
    }

    private void copyDtoToEntity(TransactionRequestDto dto, Transaction entity) {
        entity.setOperation(dto.getOperation());
        entity.setValue(dto.getValue());
    }
}
