package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.CheckingAccount;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckingAccountDto  implements Serializable {
    private static final long serialVersionUID = -8488733565017924592L;

    @EqualsAndHashCode.Include
    private Long id;

    private Double balance;

    private ClientDto client;

    private CreditCardResponseDto creditCard;

    private List<TransactionResponseDto> operations = new ArrayList<>();

    public CheckingAccountDto(CheckingAccount entity) {
        id = entity.getId();
        balance = entity.getBalance();
    }
}
