package com.wipro.techbank.services;


import com.wipro.techbank.domain.Transaction;
import com.wipro.techbank.dtos.OperationRequestDto;
import com.wipro.techbank.dtos.OperationResponseDto;
import com.wipro.techbank.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    public OperationResponseDto create(OperationRequestDto dto) {
        Transaction entity = new Transaction();
        copyDtoToEntity(dto, entity);
        entity = operationRepository.save(entity);
        return new OperationResponseDto(entity);
    }

    public Page<OperationResponseDto> findAllPaged(Pageable pageable) {
        Page<Transaction> operations = operationRepository.findAll(pageable);
        return operations.map(OperationResponseDto::new);
    }

    private void copyDtoToEntity(OperationRequestDto dto, Transaction entity) {
        entity.setDescription(dto.getDescription());
        entity.setValue(dto.getValue());
    }
}
