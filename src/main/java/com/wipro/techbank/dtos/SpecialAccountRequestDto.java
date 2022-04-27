package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class SpecialAccountRequestDto {

    @EqualsAndHashCode.Include
    private Long id;

    private Client client;

    private Double balance;

    private CreditCard creditCard;

    private Double creditSpecial;

}

