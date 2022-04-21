package com.wipro.techbank.dtos;

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
public class OperationRequestDto implements Serializable {
    private static final long serialVersionUID = 1950750423932410178L;

    private Long id;

    @NotNull(message = "Descrição da operação obrigatória.")
    private String description;

    @NotNull(message = "Valor da operação é obrigatório.")
    private Double value;

    public OperationRequestDto(Transaction entity) {
        id = entity.getId();
        description = entity.getDescription();
        value = entity.getValue();
    }
}
