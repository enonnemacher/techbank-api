package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.AccountType;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.domain.Transaction;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransactionRequestDto implements Serializable {
    private static final long serialVersionUID = 377217321776086460L;

    @NotNull(message = "Valor da operação é obrigatório.")
    private Double value;

    @NotNull(message = "O tipo da conta é obrigatório.")
    private AccountType accountType;


    public TransactionRequestDto(Transaction entity) {
        value = entity.getValue();
        accountType = entity.getAccountType();
    }
}
