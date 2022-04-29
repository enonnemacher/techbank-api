package com.wipro.techbank.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wipro.techbank.domain.AccountType;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.domain.Transaction;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransactionResponseDto implements Serializable {
    private static final long serialVersionUID = 377217321776086460L;

    private Long id;

    private LocalDateTime createdAt;

    @NotNull(message = "Descrição da operação obrigatória.")
    private Operation operation;

    @NotNull(message = "Valor da operação é obrigatório.")
    private Double value;

    @NotNull(message = "O tipo da conta é obrigatório.")
    private AccountType accountType;

    public TransactionResponseDto(Transaction entity) {
        id = entity.getId();
        createdAt = entity.getCreatedAt();
        operation = entity.getOperation();
        value = entity.getValue();
        accountType = entity.getAccountType();
    }

}
