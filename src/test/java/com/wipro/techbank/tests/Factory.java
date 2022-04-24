package com.wipro.techbank.tests;


import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.CheckingAccountResponseDto;
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

    public static CheckingAccount createCheckingAccount() {
        Client client = createClient();
        CreditCard creditCard = createCreditCard();

        return new CheckingAccount(1500.00, client, creditCard);
    }

    public static CheckingAccountResponseDto createCheckingAccountDto() {
        CheckingAccount entity = createCheckingAccount();
        CheckingAccountResponseDto checkingAccountResponseDto = new CheckingAccountResponseDto();
        checkingAccountResponseDto.setId(1L);
        checkingAccountResponseDto.setClientId(entity.getClient().getId());
        checkingAccountResponseDto.setClientName(entity.getClient().getName());
        checkingAccountResponseDto.setBalance(entity.getBalance());
        checkingAccountResponseDto.setCreditCardCardNumber(entity.getCreditCard().getCardNumber());
        checkingAccountResponseDto.setCreditCardLimitCredit(entity.getCreditCard().getLimitCredit());

        return checkingAccountResponseDto;
    }
}
