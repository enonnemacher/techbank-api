package com.wipro.techbank.services;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.domain.Transaction;
import com.wipro.techbank.dtos.SpecialAccountRequestDto;
import com.wipro.techbank.dtos.SpecialAccountResponseDto;
import com.wipro.techbank.repositories.ClientRepository;
import com.wipro.techbank.repositories.SpecialAccountRepository;
import com.wipro.techbank.repositories.TransactionRepository;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.wipro.techbank.domain.Operation.WITHDRAW;

@Service
public class SpecialAccountService {

    @Autowired
    private SpecialAccountRepository specialAccountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

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

    public void create(SpecialAccountRequestDto specialAccountRequestDto){
        Long idClient = specialAccountRequestDto.getClient().getId();
        Optional<Client> client = clientRepository.findById(idClient);
        if(!client.isPresent()){
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
        SpecialAccount specialAccount = toSpecialAccount(specialAccountRequestDto);
        specialAccountRepository.save(specialAccount);
    }

    public SpecialAccountResponseDto updateSpecialAccount(Long id, SpecialAccountRequestDto specialAccountRequestDto){
        Optional<SpecialAccount> optionalSpecialAccountDb = specialAccountRepository.findById(id);
        SpecialAccount specialAccountDb = specialAccountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Entidade não econtrada."));

        BeanUtils.copyProperties(specialAccountRequestDto,specialAccountDb);
        specialAccountRepository.save(specialAccountDb);
        return toSpecialAccountDto(specialAccountDb);
    }

    public void withdraw(Long idSpecialAccount, Double value) {

        SpecialAccount  specialAccount = specialAccountRepository.findById(idSpecialAccount).get();
        if (value <= specialAccount.getBalance()) {
            specialAccount.setBalance(specialAccount.getBalance() - value);

            Transaction transaction = new Transaction();
            transaction.setAccount(specialAccount);
            transaction.setOperation(WITHDRAW);
            transaction.setValue(value);

            transactionRepository.save(transaction);


        } else if ((specialAccount.getBalance() + specialAccount.getCreditSpecial()) >= value) {

            specialAccount.setCreditSpecialUsed((specialAccount.getBalance() - value) * (-1));

            Double newBalance =  (specialAccount.getCreditSpecialUsed() * (-1));

            specialAccount.setBalance(newBalance);

            Transaction transaction = new Transaction();
            transaction.setAccount(specialAccount);
            transaction.setOperation(WITHDRAW);
            transaction.setValue(value);

            transactionRepository.save(transaction);
        }else {
            throw new ResourceNotFoundException ("Saldo insuficiente para realizar a operação");
        }
    }

    public void remove(Long id){
        Optional<SpecialAccount> optionalSpecialAccount = specialAccountRepository.findById(id);
        if(optionalSpecialAccount.isPresent()){
            specialAccountRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Entidade não encontrada");
        }
    }

    private SpecialAccountResponseDto toSpecialAccountDto(SpecialAccount specialAccount){
        return modelMapper.map(specialAccount, SpecialAccountResponseDto.class);
    }

    private SpecialAccount toSpecialAccount(SpecialAccountRequestDto specialAccountRequestDto){
        return modelMapper.map(specialAccountRequestDto, SpecialAccount.class);
    }
}
