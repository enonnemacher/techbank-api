package com.wipro.techbank.services;

import com.wipro.techbank.domain.*;
import com.wipro.techbank.dtos.*;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.services.exceptions.DataBasesException;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;


@Service
public class CheckingAccountService {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Page<CheckingAccountResponseDto> findAll(Pageable pageable) {
        Page<CheckingAccount> checkingAccounts = checkingAccountRepository.findAll(pageable);
        return checkingAccounts.map(this::toCheckingAccountDto);
    }

    public CheckingAccountResponseDto findById(Long id) {
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(id);
        CheckingAccount checkingAccountDb = optionalCheckingAccount.orElseThrow(() ->
                new ResourceNotFoundException("Entity not found."));
        return toCheckingAccountDto(checkingAccountDb);
    }

    @Transactional
    public CheckingAccountResponseDto create(CheckingAccountRequestDto checkingAccountRequestDto){
        CheckingAccount checkingAccount = toCheckingAccount(checkingAccountRequestDto);
        checkingAccount = checkingAccountRepository.save(checkingAccount);
        return modelMapper.map(checkingAccount, CheckingAccountResponseDto.class);
    }

    @Transactional
    public CheckingAccountResponseDto updateCheckingAccount(Long id, CheckingAccountRequestDto checkingAccountRequestDto) {
        try {
            CheckingAccount entity = checkingAccountRepository.getById(id);
            entity.setBalance(checkingAccountRequestDto.getBalance());

            if (checkingAccountRequestDto.getCreditCard() != null) {
                copyDtoToEntityCreditCard(checkingAccountRequestDto, entity);
            }
            entity = checkingAccountRepository.save(entity);
            return toCheckingAccountDto(entity);
        } catch (EntityNotFoundException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new ResourceNotFoundException(String.format("Account with id %d not found.", id));
        }
    }

    public void remove(Long id) {
        try {
            checkingAccountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id " + id + " not found.");
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Integrity violation.");
        }
    }

    private CheckingAccountResponseDto toCheckingAccountDto(CheckingAccount checkingAccount) {
        return modelMapper.map(checkingAccount, CheckingAccountResponseDto.class);
    }

    public CheckingAccount toCheckingAccount(CheckingAccountRequestDto checkingAccountRequestDto){
        if (checkingAccountRequestDto.getClient() == null) {
            throw new ResourceNotFoundException("Client not found.");
        }

        try {
            Client client = clientRepository.getById(checkingAccountRequestDto.getClient().getId());
            checkingAccountRequestDto.setClient(new ClientDto(client));
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(String.format("Client with ID %d not found.", checkingAccountRequestDto.getClient().getId()));
        }

        if (checkingAccountRequestDto.getCreditCard() == null) {
            throw new ResourceNotFoundException("Credit card not found.");
        }

        try {
            CreditCard creditCard = creditCardRepository.getById(checkingAccountRequestDto.getCreditCard().getId());
            checkingAccountRequestDto.setCreditCard(new CreditCardResponseDto(creditCard));
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(String.format("Credit card with ID %d not found.", checkingAccountRequestDto.getCreditCard().getId()));
        }

        return modelMapper.map(checkingAccountRequestDto, CheckingAccount.class);
    }

    private void copyDtoToEntityCreditCard(CheckingAccountRequestDto dto, CheckingAccount entity) {
        try {
            CreditCard creditCard = creditCardRepository.getById(dto.getCreditCard().getId());
            dto.setCreditCard(new CreditCardResponseDto(creditCard));
            entity.setCreditCard(creditCard);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Credit card ID %d not found.", dto.getCreditCard().getId()));
        }
    }
}
