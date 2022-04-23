package com.wipro.techbank.services;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.*;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CheckingAccountService {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired CreditCardRepository creditCardRepository;

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

    public CheckingAccountResponseDto create(CheckingAccountRequestDto checkingAccountRequestDto){
        try {
            CheckingAccount checkingAccount = toCheckingAccount(checkingAccountRequestDto);
            checkingAccount = checkingAccountRepository.save(checkingAccount);
            return modelMapper.map(checkingAccount, CheckingAccountResponseDto.class);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("É preciso informar um cliente e um cartão de crédito já cadastrado.");
        }

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
        Client client = clientRepository.getById(checkingAccountRequestDto.getClient().getId());
        checkingAccountRequestDto.setClient(new ClientDto(client));

        CreditCard creditCard = creditCardRepository.getById(checkingAccountRequestDto.getCreditCard().getId());
        checkingAccountRequestDto.setCreditCard(new CreditCardResponseDto(creditCard));
        return modelMapper.map(checkingAccountRequestDto, CheckingAccount.class);
    }
}
