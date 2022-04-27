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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public List<CheckingAccountResponseDto> findAll() {
        return checkingAccountRepository.findAll()
                .stream()
                .map(this::toCheckingAccountDto)
                .collect(Collectors.toList());
    }

    public CheckingAccountResponseDto findById(Long id) {
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(id);
        CheckingAccount checkingAccountDb = optionalCheckingAccount.orElseThrow(() ->
                new ResourceNotFoundException("Entidade não encontrada"));
        return toCheckingAccountDto(checkingAccountDb);
    }

    @Transactional
    public CheckingAccountResponseDto create(CheckingAccountRequestDto checkingAccountRequestDto) {
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
            throw new ResourceNotFoundException(String.format("Conta com id %d não foi encontrada.", id));
        }
    }

    public void remove(Long id) {
        try {
            checkingAccountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Integrity violation");
        }
    }

    private CheckingAccountResponseDto toCheckingAccountDto(CheckingAccount checkingAccount) {
        return modelMapper.map(checkingAccount, CheckingAccountResponseDto.class);
    }

    public CheckingAccount toCheckingAccount(CheckingAccountRequestDto checkingAccountRequestDto) {
        if (checkingAccountRequestDto.getClient() == null) {
            throw new ResourceNotFoundException("Cliente, cadastrado, precisa ser informado");
        }

        try {
            Client client = clientRepository.getById(checkingAccountRequestDto.getClient().getId());
            checkingAccountRequestDto.setClient(modelMapper.map(client, ClientDto.class));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Cliente com ID %d não foi encontrado.", checkingAccountRequestDto.getClient().getId()));
        }

        if (checkingAccountRequestDto.getCreditCard() == null) {
            throw new ResourceNotFoundException("Cartão de Credito, cadastrado, precisa ser informado.");
        }

        try {
            CreditCard creditCard = creditCardRepository.getById(checkingAccountRequestDto.getCreditCard().getId());
            checkingAccountRequestDto.setCreditCard(new CreditCardResponseDto(creditCard));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Cartão de Credito com ID %d não foi encontrado.", checkingAccountRequestDto.getCreditCard().getId()));
        }

        return modelMapper.map(checkingAccountRequestDto, CheckingAccount.class);
    }

    private void copyDtoToEntityCreditCard(CheckingAccountRequestDto dto, CheckingAccount entity) {
        try {
            CreditCard creditCard = creditCardRepository.getById(dto.getCreditCard().getId());
            dto.setCreditCard(new CreditCardResponseDto(creditCard));
            entity.setCreditCard(creditCard);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Cartão de Credito com ID %d não foi encontrado.", dto.getCreditCard().getId()));
        }
    }
}
