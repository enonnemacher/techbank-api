package com.wipro.techbank.tests;


import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.CreditCardRequestDto;
import com.wipro.techbank.dtos.CreditCardResponseDto;

public class Factory {

    public static CreditCard createCreditCard() {
        return new CreditCard(1L, 600.00);
    }

    public static CreditCardResponseDto createCreditCardDto() {
        CreditCard entity = createCreditCard();
        return new CreditCardResponseDto(entity);
    }

    public static CreditCardRequestDto createCreditCardRequestDto() {
        CreditCard entity = createCreditCard();
        return new CreditCardRequestDto(entity);
    }

    public static Client createClient() {
        return new Client("Fulano Beltrano", "123.456.789-00", "(10) 91998-9673", "emailteste2022@teste.com");
    }
}
