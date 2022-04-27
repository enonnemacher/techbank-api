package com.wipro.techbank.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CheckingAccountResponseDto {

    @EqualsAndHashCode.Include
    private Long id;

    private Long ClientId;

    private String ClientName;

    private Double balance;

    private String creditCardCardNumber;

    private Double creditCardLimitCredit;
}