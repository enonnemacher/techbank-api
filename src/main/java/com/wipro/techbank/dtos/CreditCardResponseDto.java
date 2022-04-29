package com.wipro.techbank.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wipro.techbank.domain.CreditCard;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "ID", example = "1", allowableValues = "1, 2, 3, 4", required = true)
    private Long id;

    @ApiModelProperty(notes = "Credit card number", example = "4527 0144 9327 6163", required = true)
    private String cardNumber;


    @JsonFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(notes = "Expiration date", example = "2022-04-29T17:09:00.842Z", required = true)
    private LocalDateTime expirationDate;

    @ApiModelProperty(notes = "security code", example = "551", allowableValues = "123, 321, 654, 987", required = true)
    private Short securityCode;

    @NotNull(message = "Limite inicial é obrigatório.")
    @Range(min=200, max = 5000, message = "O valor inicial do limite precisar ser maior que 200.00 reais e menor que 5000.00 mil reais.")
    @ApiModelProperty(notes = "Credit card limit", example = "800.00", allowableValues = "200.00, 5000.00", required = true)
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
