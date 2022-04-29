package com.wipro.techbank.dtos;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransactionBalanceDto implements Serializable {
    private static final long serialVersionUID = 4993614957935344131L;

    private Double balance;

}
