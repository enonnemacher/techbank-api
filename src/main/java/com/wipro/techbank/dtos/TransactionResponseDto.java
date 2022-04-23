package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.Account;
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
    private LocalDateTime date;

    @NotNull(message = "Descrição da operação obrigatória.")
    private Operation operation;

    @NotNull(message = "Valor da operação é obrigatório.")
    private Double value;
    @NotNull(message = "A conta é obrigatória.")
    private Account account;

    public TransactionResponseDto(Transaction entity) {
        id = entity.getId();
        account = entity.getAccount();
        date = entity.getCreatedAt();
        operation = entity.getOperation();
        value = entity.getValue();
    }
}
