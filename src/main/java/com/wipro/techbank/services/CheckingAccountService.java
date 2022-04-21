package com.wipro.techbank.services;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.Transaction;
import com.wipro.techbank.dtos.*;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.repositories.TransactionRepository;
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
    private TransactionRepository transactionRepository;

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
        Client client = new Client();
        client.setCpf(dto.getClient().getCpf());
        copyClientDtoToClientEntity(entity.getClient(), dto.getClient());

        // TODO: falta setar o cartão de credito

        entity.getTransactions().clear();
        for (TransactionResponseDto transactionResponseDto : dto.getOperations()) {
            Transaction transaction = transactionRepository.getById(transactionResponseDto.getId());
            entity.getTransactions().add(transaction);
        }
    }

    // TODO: pensar uma melhor forma de criar o método
    private void copyClientDtoToClientEntity(Client entity, ClientDto dto) {
        entity.setId(dto.getId());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setPhoneNumber(dto.getPhoneNumber());
    }

}
