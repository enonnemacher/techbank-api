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
                new ResourceNotFoundException("Entidade não encontrada"));
        return toSpecialAccountDto(specialAccountDb);
    }

    public SpecialAccountResponseDto create(SpecialAccountRequestDto specialAccountRequestDto) {
        Long idClient = specialAccountRequestDto.getClient().getId();
        Optional<Client> client = clientRepository.findById(idClient);
        if (!client.isPresent()) {
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
        SpecialAccount specialAccount = toSpecialAccount(specialAccountRequestDto);
        specialAccountRepository.save(specialAccount);
        return toSpecialAccountDto(specialAccount);

    }

    public SpecialAccountResponseDto updateSpecialAccount(Long id, SpecialAccountRequestDto specialAccountRequestDto) {
        Optional<SpecialAccount> optionalSpecialAccountDb = specialAccountRepository.findById(id);
        SpecialAccount specialAccountDb = specialAccountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Entidade não econtrada."));

        BeanUtils.copyProperties(specialAccountRequestDto, specialAccountDb);
        specialAccountRepository.save(specialAccountDb);
        return toSpecialAccountDto(specialAccountDb);
    }

    public void remove(Long id) {
        Optional<SpecialAccount> optionalSpecialAccount = specialAccountRepository.findById(id);
        if (optionalSpecialAccount.isPresent()) {
            specialAccountRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
    }

    private SpecialAccountResponseDto toSpecialAccountDto(SpecialAccount specialAccount) {
        return modelMapper.map(specialAccount, SpecialAccountResponseDto.class);
    }

    private SpecialAccount toSpecialAccount(SpecialAccountRequestDto specialAccountRequestDto) {
        if (specialAccountRequestDto.getClient().equals(null)) {
            throw new ResourceNotFoundException("Cliente, cadastrado, precisa ser informado");
        }

        try {
            Client client = clientRepository.getById(specialAccountRequestDto.getClient().getId());
            specialAccountRequestDto.setClient(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Cliente com ID %d não foi encontrado.", specialAccountRequestDto.getClient().getId()));
        }

        if (specialAccountRequestDto.getCreditCard().equals(null)) {
            throw new ResourceNotFoundException("Cartão de Credito, cadastrado, precisa ser informado.");
        }

        try {
            CreditCard creditCard = creditCardRepository.getById(specialAccountRequestDto.getCreditCard().getId());
            specialAccountRequestDto.setCreditCard(creditCard);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Cartão de Credito com ID %d não foi encontrado.", specialAccountRequestDto.getCreditCard().getId()));
        }

        return modelMapper.map(specialAccountRequestDto, SpecialAccount.class);
    }
}