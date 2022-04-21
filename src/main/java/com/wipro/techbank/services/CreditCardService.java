package com.wipro.techbank.services;

import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.CreditCardResponseDto;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.repositories.CreditCardRepository;
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
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCardResponseDto create(CreditCardRequestDto dto) {
        CreditCard entity = new CreditCard();
        entity.setLimitCredit(dto.getLimitCredit());
        entity = creditCardRepository.save(entity);
        return new CreditCardResponseDto(entity);
    }

    public Page<CreditCardResponseDto> findAllPaged(Pageable pageable) {
        Page<CreditCard> creditCards = creditCardRepository.findAll(pageable);
        return creditCards.map(CreditCardResponseDto::new);
    }

    @Transactional(readOnly = true)
    public CreditCardResponseDto findById(Long id) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(id);
        CreditCard entity = optionalCreditCard.orElseThrow(() -> new ResourceNotFoundException("Entidade não econtrada."));
        return new CreditCardResponseDto(entity);
    }

    public void delete(Long id) {
        try {
            creditCardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado.");
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Violação de integridade.");
        }
    }


}
