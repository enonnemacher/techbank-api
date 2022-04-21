package com.wipro.techbank.dtos;

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
public class OperationResponseDto implements Serializable {
    private static final long serialVersionUID = 377217321776086460L;

    private Long id;
    private LocalDateTime date;

    @NotNull(message = "Descrição da operação obrigatória.")
    private String description;

    @NotNull(message = "Valor da operação é obrigatório.")
    private Double value;

    public OperationResponseDto(Transaction entity) {
        id = entity.getId();
        date = entity.getDate();
        description = entity.getDescription();
        value = entity.getValue();
    }
}
