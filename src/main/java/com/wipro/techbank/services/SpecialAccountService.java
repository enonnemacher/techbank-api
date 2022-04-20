package com.wipro.techbank.services;

import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.dtos.CreditCardResponseDto;
import com.wipro.techbank.dtos.OperationRequestDto;
import com.wipro.techbank.dtos.SpecialAccountDto;
import com.wipro.techbank.repositories.SpecialAccountRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialAccountService {

    @Autowired
    SpecialAccountRepository specialAccountRepository;

    // public SpecialAccount save(SpecialAccount specialAccount){ return specialAccountRepository.save(specialAccount);}

    public SpecialAccountDto create(SpecialAccountDto dto) {
        SpecialAccount entity = new SpecialAccount();
        copyDtoToEntity(dto, entity);
        entity = specialAccountRepository.save(entity);
        return new SpecialAccountDto(entity);
    }

    public Page<SpecialAccountDto> findAllPaged(Pageable pageable) {
        Page<SpecialAccount> specialAccounts = specialAccountRepository.findAll(pageable);
        return specialAccounts.map(SpecialAccountDto::new);
    }

    public SpecialAccountDto findById(Long id){
        SpecialAccount specialAccount = specialAccountRepository.findById(id).get();
        SpecialAccountDto dto = new SpecialAccountDto();
        return copyDtoToDTO(dto, specialAccount);
    }

    public SpecialAccount update(Long id, SpecialAccount specialAccount){
        SpecialAccount account = specialAccountRepository.findById(id).orElseGet(() ->{
            throw new ResourceNotFoundException("Conta não encontrada");
        });
        return specialAccountRepository.save(account);
    }

    public void delete(Long id){
        try {
            specialAccountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado.");
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Violação de integridade.");
        }
    }

    private void copyDtoToEntity(SpecialAccountDto dto, SpecialAccount entity) {
        entity.setId(dto.getId());
        entity.setBalance(dto.getBalance());
        entity.setCreditSpecial(dto.getCreditSpecial());
        entity.setCreditSpecialUsed(dto.getCreditSpecialUsed());
    }

    private SpecialAccountDto copyDtoToDTO(SpecialAccountDto dto, SpecialAccount entity) {
        dto.setId(entity.getId());
        dto.setBalance(entity.getBalance());
        dto.setCreditSpecial(entity.getCreditSpecial());
        dto.setCreditSpecialUsed(entity.getCreditSpecialUsed());
        return dto;
    }
}
