package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.CreditCard;
import io.swagger.annotations.ApiModelProperty;
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

    @NotNull
    @ApiModelProperty(notes = "Credit card limit", example = "800.00", allowableValues = "200.00, 5000.00", required = true)
    private Double limitCredit;


    public CreditCardRequestDto(CreditCard entity) {
        limitCredit = entity.getLimitCredit();
    }
}
