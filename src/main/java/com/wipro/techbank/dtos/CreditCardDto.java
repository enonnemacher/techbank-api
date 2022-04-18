package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.CreditCard;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreditCardDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String cardNumber;

    @NotNull
    private LocalDateTime expirationDate;

    @NotNull
    private Short securityCode;

    @NotNull
    private Double limitCredit;

    private Double usedLimit;

    public CreditCardDto(CreditCard entity) {
        id = entity.getId();
        cardNumber = entity.getCardNumber();
        expirationDate = entity.getExpirationDate();
        securityCode = entity.getSecurityCode();
        limitCredit = entity.getLimitCredit();
        usedLimit = entity.getUsedLimit();
    }
}
