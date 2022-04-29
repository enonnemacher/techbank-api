package com.wipro.techbank.services;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.dtos.*;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.repositories.CreditCardRepository;
import com.wipro.techbank.repositories.SpecialAccountRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialAccountService {

    @Autowired
    private SpecialAccountRepository specialAccountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CreditCardRepository creditCardRepository;

    public List<SpecialAccountResponseDto> findAll() {
        return specialAccountRepository.findAll()
                .stream()
                .map(this::toSpecialAccountDto)
                .collect(Collectors.toList());
    }

    public SpecialAccountResponseDto findById(Long id) {
        Optional<SpecialAccount> optionalSpecialAccount = specialAccountRepository.findById(id);
        SpecialAccount specialAccountDb = optionalSpecialAccount.orElseThrow(() ->
                new ResourceNotFoundException("Entity not found."));
        return toSpecialAccountDto(specialAccountDb);
    }

    public SpecialAccountResponseDto create(SpecialAccountRequestDto specialAccountRequestDto) {
        SpecialAccount specialAccount = toSpecialAccount(specialAccountRequestDto);
        specialAccount = specialAccountRepository.save(specialAccount);
        return modelMapper.map(specialAccount, SpecialAccountResponseDto.class);
    }

    public SpecialAccountResponseDto updateSpecialAccount(Long id, SpecialAccountRequestDto specialAccountRequestDto) {
        Optional<SpecialAccount> optionalSpecialAccountDb = specialAccountRepository.findById(id);
        SpecialAccount specialAccountDb = specialAccountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Entity not found."));

        BeanUtils.copyProperties(specialAccountRequestDto, specialAccountDb);
        specialAccountRepository.save(specialAccountDb);
        return toSpecialAccountDto(specialAccountDb);
    }

    public void remove(Long id){
        try {
            specialAccountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id " + id + " not found.");
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Integrity violation.");
        }
    }

    private SpecialAccountResponseDto toSpecialAccountDto(SpecialAccount specialAccount) {
        return modelMapper.map(specialAccount, SpecialAccountResponseDto.class);
    }

    private SpecialAccount toSpecialAccount(SpecialAccountRequestDto specialAccountRequestDto) {
        if (specialAccountRequestDto.getClient() == null) {
            throw new ResourceNotFoundException("Client not found.");
        }

        try {
            Client client = clientRepository.getById(specialAccountRequestDto.getClient().getId());
            specialAccountRequestDto.setClient(client);
        } catch (javax.persistence.EntityNotFoundException | org.modelmapper.MappingException | org.hibernate.exception.ConstraintViolationException e) {
            throw new ResourceNotFoundException(String.format("Client with ID %d not found.", specialAccountRequestDto.getClient().getId()));
        }

        if (specialAccountRequestDto.getCreditCard() == null) {
            throw new ResourceNotFoundException("Credit card not found.");
        }

        try {
            CreditCard creditCard = creditCardRepository.getById(specialAccountRequestDto.getCreditCard().getId());
            specialAccountRequestDto.setCreditCard(creditCard);
        } catch (javax.persistence.EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Credit card ID %d not found.", specialAccountRequestDto.getCreditCard().getId()));
        }

        return modelMapper.map(specialAccountRequestDto, SpecialAccount.class);
    }
}