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
public class TransactionResponseExtractDto implements Serializable {
    private static final long serialVersionUID = 3351126276354159130L;

    private LocalDateTime createdAt;

    @NotNull(message = "Descrição da operação obrigatória.")
    private Operation operation;

    @NotNull(message = "Valor da operação é obrigatório.")
    private Double value;

}
