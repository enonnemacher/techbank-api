package com.wipro.techbank.services;

import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.CreditCardResponseDto;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;


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

    private void copyDtoToEntity(CreditCardRequestDto dto, CreditCard entity) {
        Double initialUsedLimit = 0.0;

        entity.setCardNumber(generateNumbers(LENGTH_CARD_NUMBER));
        entity.setExpirationDate(generateExpirationDate());
        entity.setSecurityCode(Short.valueOf(generateNumbers(LENGTH_SECURITY_CODE)));
        entity.setLimitCredit(dto.getLimitCredit());
        entity.setUsedLimit(initialUsedLimit);
    }

    private String generateNumbers(int limit) {
        String number = "";
        int range = 10;

        for (int index = 0; index < limit; index++) {
            int random = (int) (Math.random() * range);
            if (index % 4 == 0 && index > 0) {
                number += " ";
            }
            number += random;
        }
        return number;
    }

    private LocalDateTime generateExpirationDate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, NUMBER_TO_EXPIRATION);
        return  now.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }


}
