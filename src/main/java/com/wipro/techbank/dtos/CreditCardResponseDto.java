package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.CreditCard;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCardResponseDto implements Serializable {
    private static final long serialVersionUID = 377217321776086460L;

    @EqualsAndHashCode.Include
    private Long id;

    private String cardNumber;

    private LocalDateTime expirationDate;

    private Short securityCode;

    @NotNull(message = "Limite inicial é obrigatório.")
    @Range(min=200, max = 5000, message = "O valor inicial do limite precisar ser maior que 200.00 reais e menor que 5000.00 mil reais.")
    private Double limitCredit;

    private Double usedLimit;

    public CreditCardResponseDto(CreditCard entity) {
        if (entity != null) {
        id = entity.getId();
        cardNumber = entity.getCardNumber();
        expirationDate = entity.getExpirationDate();
        securityCode = entity.getSecurityCode();
        limitCredit = entity.getLimitCredit();
        usedLimit = entity.getUsedLimit();
        }
    }


}
