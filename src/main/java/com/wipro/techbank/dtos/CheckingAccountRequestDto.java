package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.CreditCard;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckingAccountRequestDto implements Serializable {
    private static final long serialVersionUID = -8488733565017924592L;

    @EqualsAndHashCode.Include
    private Long id;

    private Double balance;

    private ClientDto client;

    private CreditCard creditCard;

//    private List<TransactionResponseDto> operations = new ArrayList<>();
//
//    public CheckingAccountDto(CheckingAccount entity) {
//        id = entity.getId();
//        balance = entity.getBalance();
//    }
}
