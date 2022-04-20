package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.Operation;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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

    public OperationResponseDto(Operation entity) {
        id = entity.getId();
        date = entity.getDate();
        description = entity.getDescription();
        value = entity.getValue();
    }
}
