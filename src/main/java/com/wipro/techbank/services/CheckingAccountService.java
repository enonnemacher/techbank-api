package com.wipro.techbank.services;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.dtos.*;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.repositories.OperationRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CheckingAccountService {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private OperationRepository operationRepository;

    public CheckingAccountDto create(CheckingAccountDto dto) {
        CheckingAccount entity = new CheckingAccount();
        copyDtoToEntity(dto, entity);
        entity = checkingAccountRepository.save(entity);
        return new CheckingAccountDto(entity);
    }

    public Page<CheckingAccountDto> findAllPaged(Pageable pageable) {
        Page<CheckingAccount> creditCards = checkingAccountRepository.findAll(pageable);
        return creditCards.map(CheckingAccountDto::new);
    }

    @Transactional(readOnly = true)
    public CheckingAccountDto findById(Long id) {
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(id);
        CheckingAccount entity = optionalCheckingAccount.orElseThrow(() -> new ResourceNotFoundException("Entidade não econtrada."));
        return new CheckingAccountDto(entity);
    }

    public void delete(Long id) {
        try {
            checkingAccountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado.");
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Violação de integridade.");
        }
    }

    private void copyDtoToEntity(CheckingAccountDto dto, CheckingAccount entity) {
        entity.setBalance(dto.getBalance());

        entity.getClients().clear();
        for (ClientDto clientDto : dto.getClients()) {
            Client client = clientRepository.getById(clientDto.getId());
            entity.getClients().add(client);
        }
        entity.getCreditCards().clear();
        for (CreditCardResponseDto creditCardDto : dto.getCreditCards()) {
            CreditCard creditCard = creditCardRepository.getById(creditCardDto.getId());
            entity.getCreditCards().add(creditCard);
        }
        entity.getOperations().clear();
        for (OperationResponseDto operationResponseDto : dto.getOperations()) {
            Operation operation = operationRepository.getById(operationResponseDto.getId());
            entity.getOperations().add(operation);
        }
    }
}
