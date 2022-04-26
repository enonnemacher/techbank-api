package com.wipro.techbank.tests;


import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.dtos.*;

import java.util.List;

import static com.wipro.techbank.tests.FactoryClient.CLIENT_DTO_TEST;
import static com.wipro.techbank.tests.FactoryClient.CLIENT_TEST;
import static com.wipro.techbank.tests.FactoryCreditCard.CREDIT_CARD_RESPONSE_DTO_TEST;
import static com.wipro.techbank.tests.FactoryCreditCard.CREDIT_CARD_TEST;

public class FactoryAccounts {
    private static final Double BALANCE = 1500.00;
    private static final Double SPECIAL_CREDIT = 1700.00;



    public static CheckingAccount createCheckingAccount() {
        return new CheckingAccount(BALANCE, CLIENT_TEST, CREDIT_CARD_TEST);
    }

    public static List<CheckingAccount> creteCheckingAccountList() {

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

    public static SpecialAccount createSpecialAccount() {
        return new SpecialAccount(CLIENT_TEST, CREDIT_CARD_TEST, SPECIAL_CREDIT, 0.00);
    }

    public static CheckingAccountRequestDto createCheckingAccountRequestDto() {
        CheckingAccountRequestDto checkingAccountRequestDto = new CheckingAccountRequestDto();
        checkingAccountRequestDto.setId(1L);
        checkingAccountRequestDto.setClient(CLIENT_DTO_TEST);
        checkingAccountRequestDto.getClient().setId(1L);
        checkingAccountRequestDto.setCreditCard(CREDIT_CARD_RESPONSE_DTO_TEST);
        checkingAccountRequestDto.setBalance(BALANCE);
        return checkingAccountRequestDto;
    }

    public static SpecialAccountResponseDto createSpecialAccountResponseDto() {
        CheckingAccount entity = createCheckingAccount();
        SpecialAccountResponseDto specialAccountRequestDto = new SpecialAccountResponseDto();
        specialAccountRequestDto.setId(1L);
        specialAccountRequestDto.setClientId(entity.getClient().getId());
        specialAccountRequestDto.setClientName(entity.getClient().getName());
        specialAccountRequestDto.setBalance(entity.getBalance());
        specialAccountRequestDto.setCreditCardCardNumber(entity.getCreditCard().getCardNumber());
        specialAccountRequestDto.setCreditCardLimitCredit(entity.getCreditCard().getLimitCredit());
        specialAccountRequestDto.setCreditSpecialUsed(entity.getCreditCard().getUsedLimit());
        specialAccountRequestDto.setCreditSpecial(SPECIAL_CREDIT);

        return specialAccountRequestDto;
    }

    public static SpecialAccountRequestDto createSpecialRequestAccount() {
        SpecialAccountRequestDto specialAccountRequestDto = new SpecialAccountRequestDto();
        specialAccountRequestDto.setId(1L);
        specialAccountRequestDto.setClient(CLIENT_TEST);
        specialAccountRequestDto.getClient().setId(1L);
        specialAccountRequestDto.setCreditCard(CREDIT_CARD_TEST);
        specialAccountRequestDto.setBalance(BALANCE);
        return specialAccountRequestDto;
    }
}
