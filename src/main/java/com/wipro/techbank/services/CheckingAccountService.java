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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    public CheckingAccountResponseDto create(CheckingAccountRequestDto checkingAccountRequestDto){
        try {
            CheckingAccount checkingAccount = toCheckingAccount(checkingAccountRequestDto);
            checkingAccount = checkingAccountRepository.save(checkingAccount);
            return modelMapper.map(checkingAccount, CheckingAccountResponseDto.class);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("É preciso informar um cliente e um cartão de crédito já cadastrado.");
        }

    }

    @Transactional
    public CheckingAccountResponseDto updateCheckingAccount(Long id, CheckingAccountRequestDto checkingAccountRequestDto){
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

    private void copyDtoToEntityClient(CheckingAccountRequestDto dto, CheckingAccount entity) {
        Client client = clientRepository.getById(dto.getClient().getId());
        dto.setClient(new ClientDto(client));
        entity.setClient(client);
    }

    private void copyDtoToEntityCreditCard(CheckingAccountRequestDto dto, CheckingAccount entity) {
        try {
            CreditCard creditCard = creditCardRepository.getById(dto.getCreditCard().getId());
            dto.setCreditCard(new CreditCardResponseDto(creditCard));
            entity.setCreditCard(creditCard);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(String.format("Cartão de Credito com ID %d não foi encontrado.", dto.getCreditCard().getId()));
        }
    }
}
