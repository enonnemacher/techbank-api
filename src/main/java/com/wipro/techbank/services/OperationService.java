package com.wipro.techbank.services;


import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.dtos.CreditCardResponseDto;
import com.wipro.techbank.dtos.OperationRequestDto;
import com.wipro.techbank.dtos.OperationResponseDto;
import com.wipro.techbank.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    public OperationResponseDto create(OperationRequestDto dto) {
        Operation entity = new Operation();
        copyDtoToEntity(dto, entity);
        entity = operationRepository.save(entity);
        return new OperationResponseDto(entity);
    }

    public Page<OperationResponseDto> findAllPaged(Pageable pageable) {
        Page<Operation> operations = operationRepository.findAll(pageable);
        return operations.map(OperationResponseDto::new);
    }

    private void copyDtoToEntity(OperationRequestDto dto, Operation entity) {
        entity.setDescription(dto.getDescription());
        entity.setValue(dto.getValue());
    }
}
