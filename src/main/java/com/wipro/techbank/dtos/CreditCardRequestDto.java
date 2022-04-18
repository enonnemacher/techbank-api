package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.CreditCard;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCardRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private Double limitCredit;


    public CreditCardRequestDto(CreditCard entity) {
        id = entity.getId();
        limitCredit = entity.getLimitCredit();
    }
}
