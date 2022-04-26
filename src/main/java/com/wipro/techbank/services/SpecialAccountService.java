package com.wipro.techbank.services;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.dtos.CheckingAccountResponseDto;
import com.wipro.techbank.dtos.SpecialAccountRequestDto;
import com.wipro.techbank.dtos.SpecialAccountResponseDto;
import com.wipro.techbank.repositories.ClientRepository;
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

    public List<SpecialAccountResponseDto> findAll(){
        return specialAccountRepository.findAll()
                .stream()
                .map(this::toSpecialAccountDto)
                .collect(Collectors.toList());
    }

    public SpecialAccountResponseDto findById(Long id){
        Optional<SpecialAccount> optionalSpecialAccount = specialAccountRepository.findById(id);
        SpecialAccount specialAccountDb = optionalSpecialAccount.orElseThrow(()->
                new ResourceNotFoundException("Entidade não encontrada"));
        return toSpecialAccountDto(specialAccountDb);
    }

    public SpecialAccountResponseDto create(SpecialAccountRequestDto specialAccountRequestDto){
        Long idClient = specialAccountRequestDto.getClient().getId();
        Optional<Client> client = clientRepository.findById(idClient);
        if(!client.isPresent()){
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
        SpecialAccount specialAccount = toSpecialAccount(specialAccountRequestDto);
        specialAccount = specialAccountRepository.save(specialAccount);
        return toSpecialAccountDto(specialAccount);
    }

    public SpecialAccountResponseDto updateSpecialAccount(Long id, SpecialAccountRequestDto specialAccountRequestDto){
        Optional<SpecialAccount> optionalSpecialAccountDb = specialAccountRepository.findById(id);
        SpecialAccount specialAccountDb = specialAccountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Entidade não econtrada."));

        BeanUtils.copyProperties(specialAccountRequestDto,specialAccountDb);
        specialAccountRepository.save(specialAccountDb);
        return toSpecialAccountDto(specialAccountDb);
    }

    public void remove(Long id) {
        try {
            specialAccountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Integrity violation");
        }
    }

    private SpecialAccountResponseDto toSpecialAccountDto(SpecialAccount specialAccount){
        return modelMapper.map(specialAccount, SpecialAccountResponseDto.class);
    }

    private SpecialAccount toSpecialAccount(SpecialAccountRequestDto specialAccountRequestDto){
        return modelMapper.map(specialAccountRequestDto, SpecialAccount.class);
    }
}
