package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.Operation;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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

    public OperationRequestDto(Operation entity) {
        id = entity.getId();
        description = entity.getDescription();
        value = entity.getValue();
    }
}
