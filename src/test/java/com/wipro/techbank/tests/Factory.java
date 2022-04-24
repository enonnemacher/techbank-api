package com.wipro.techbank.tests;


import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.ClientDto;
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
        return new Client("Fulano Beltrano dos Testes", "756.394.430-30", "(10) 91998-9673", "fulano.beltrano.testes@techbank.com");
    }

    public static ClientDto createClientDto() {
        Client entity = createClient();
        return new ClientDto(entity);
    }
}
