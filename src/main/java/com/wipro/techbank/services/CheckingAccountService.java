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
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ModelMapper modelMapper;

    public List<CheckingAccountResponseDto> findAll(){
        return checkingAccountRepository.findAll()
                .stream()
                .map(this::toCheckingAccountDto)
                .collect(Collectors.toList());
    }

    public CheckingAccountResponseDto findById(Long id){
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(id);
        CheckingAccount checkingAccountDb = optionalCheckingAccount.orElseThrow(()->
                new ResourceNotFoundException("Entidade não encontrada"));
        return toCheckingAccountDto(checkingAccountDb);
    }

    public void create(CheckingAccountRequestDto checkingAccountRequestDto){
        Long idClient = checkingAccountRequestDto.getClient().getId();
        Optional<Client> client = clientRepository.findById(idClient);
        if(!client.isPresent()){
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
        CheckingAccount checkingAccount = toCheckingAccount(checkingAccountRequestDto);
        checkingAccountRepository.save(checkingAccount);
    }

    public CheckingAccountResponseDto updateCheckingAccount(Long id, CheckingAccountRequestDto checkingAccountRequestDto){
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(id);
        CheckingAccount checkingAccountDb = optionalCheckingAccount.orElseThrow(()->
                new ResourceNotFoundException("Entidade não encontrada"));

        BeanUtils.copyProperties(checkingAccountRequestDto,checkingAccountDb);
        checkingAccountRepository.save(checkingAccountDb);
        return toCheckingAccountDto(checkingAccountDb);
    }

    public void remove(Long id){
        checkingAccountRepository.deleteById(id);
    }

    private CheckingAccountResponseDto toCheckingAccountDto(CheckingAccount checkingAccount){
        return modelMapper.map(checkingAccount, CheckingAccountResponseDto.class);
    }

    private CheckingAccount toCheckingAccount(CheckingAccountRequestDto checkingAccountRequestDto){
        return modelMapper.map(checkingAccountRequestDto, CheckingAccount.class);
    }
}
