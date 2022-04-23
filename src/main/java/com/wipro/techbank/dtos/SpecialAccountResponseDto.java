package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.SpecialAccount;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class SpecialAccountResponseDto {

    @EqualsAndHashCode.Include
    private Long id;

    private Long ClientId;

    private String ClientName;

    private Double balance;

    private String creditCardCardNumber;

    private Double creditCardLimitCredit;

    private Double creditSpecial;

    private Double creditSpecialUsed;

}
