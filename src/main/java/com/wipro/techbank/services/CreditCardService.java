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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;


@Service
public class CreditCardService {

    private static final Integer LENGTH_CARD_NUMBER = 16;
    private static final Integer LENGTH_SECURITY_CODE = 3;
    private static final Integer NUMBER_TO_EXPIRATION = 5;

    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCardResponseDto create(CreditCardRequestDto dto) {
        CreditCard entity = new CreditCard();
        copyDtoToEntity(dto, entity);
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

    private void copyDtoToEntity(CreditCardRequestDto dto, CreditCard entity) {
        Double initialUsedLimit = 0.0;

        entity.setCardNumber(generateNumbers(LENGTH_CARD_NUMBER));
        entity.setExpirationDate(generateExpirationDate());
        entity.setSecurityCode(Short.valueOf(generateNumbers(LENGTH_SECURITY_CODE)));
        entity.setLimitCredit(dto.getLimitCredit());
        entity.setUsedLimit(initialUsedLimit);
    }

    private String generateNumbers(int limit) {
        StringBuilder number = new StringBuilder();
        int range = 10;

        for (int index = 0; index < limit; index++) {
            int random = (int) (Math.random() * range);
            if (index % 4 == 0 && index > 0) {
                number.append(" ");
            }
            number.append(random);
        }
        return number.toString();
    }

    private LocalDateTime generateExpirationDate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, NUMBER_TO_EXPIRATION);
        return  now.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }


}
