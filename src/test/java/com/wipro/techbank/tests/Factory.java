package com.wipro.techbank.tests;


import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.dtos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Factory {

    public static CreditCard createCreditCard() {
        CreditCard creditCard = new CreditCard(500.00);
        creditCard.setId(1L);
        creditCard.setCardNumber("4527 0144 9327 6163");
        creditCard.setExpirationDate(LocalDateTime.of(2023, 07, 13, 0, 0));
        creditCard.setUsedLimit(0.0);
        creditCard.setSecurityCode((short) 456);
        return creditCard;
    }

    public static List<CreditCard> createCreditCardList() {
        List<CreditCard> list = new ArrayList<>();
        for(int index = 1; index <= 10; index++) {
            CreditCard creditCard = new CreditCard(500.00);
            list.add(creditCard);
        }
        return list;
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
        Client client = new Client("Fulano Beltrano dos Testes", "756.394.430-30", "(10) 91998-9673", "fulano.beltrano.testes@techbank.com");
        return client;
    }

    public static List<Client> createClientList() {
        List<Client> list = new ArrayList<>();
        for(int index = 1; index <= 10; index++) {
            Client client = new Client("Fulano Beltrano dos Teste"+index, "756.394.430-3"+(index-1), "(10) 91998-967"+(index-1), String.format("fulano.beltrano.testes%d@techbank.com", index));
            list.add(client);
        }
        return list;
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

    public static List<CheckingAccount> creteCheckingAccountList() {
        List<Client> clients = createClientList();

        return null;
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

    public static CheckingAccountRequestDto createCheckingAccountRequestDto() {
        ClientDto clientDto = new ClientDto(1L, "Fulano Beltrano dos Testes", "756.394.430-30", "(10) 91998-9673", "fulano.beltrano.testes@techbank.com");
        CheckingAccountRequestDto checkingAccountRequestDto = new CheckingAccountRequestDto();
        checkingAccountRequestDto.setId(1L);
        checkingAccountRequestDto.setClient(clientDto);
        checkingAccountRequestDto.getClient().setId(1L);
        checkingAccountRequestDto.setCreditCard(createCreditCardDto());
        checkingAccountRequestDto.setBalance(1500.00);
        return checkingAccountRequestDto;
    }
}
