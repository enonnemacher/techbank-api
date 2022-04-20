package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.Client;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClientDto {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String cpf;

    private String phoneNumber;

    private String email;

    public ClientDto(Client entity) {
        id = entity.getId();
        name = entity.getName();
        cpf = entity.getCpf();
        phoneNumber = entity.getPhoneNumber();
        email = entity.getPhoneNumber();
    }
}
