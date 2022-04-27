package com.wipro.techbank.tests;


import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FactoryCreditCard {
    private static final Double LIMIT_CREDIT = 500.00;
    private static final String CARD_NUMBER = "4527 0144 9327 6163";
    private static final LocalDateTime EXPIRATION_DATE = LocalDateTime.of(2023, 07, 13, 0, 0);
    private static final Short SECURITY_CODE = 456;

    public static final CreditCard CREDIT_CARD_TEST = createCreditCard();
    public static final List<CreditCard> CREDIT_CARDS_LIST_TEST = createCreditCardList();
    public static final CreditCardResponseDto CREDIT_CARD_RESPONSE_DTO_TEST = new CreditCardResponseDto(CREDIT_CARD_TEST);
    public static final CreditCardRequestDto CREDIT_CARD_REQUEST_DTO_TEST = new CreditCardRequestDto(CREDIT_CARD_TEST);

    private static CreditCard createCreditCard() {
        CreditCard creditCard = new CreditCard(LIMIT_CREDIT);
        creditCard.setId(1L);
        creditCard.setCardNumber(CARD_NUMBER);
        creditCard.setExpirationDate(EXPIRATION_DATE);
        creditCard.setUsedLimit(0.0);
        creditCard.setSecurityCode(SECURITY_CODE);
        return creditCard;
    }

    private static List<CreditCard> createCreditCardList() {
        List<CreditCard> list = new ArrayList<>();
        for(int index = 1; index <= 10; index++) {
            CreditCard creditCard = new CreditCard(LIMIT_CREDIT);
            list.add(creditCard);
        }
        return list;
    }
}
