package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
import com.wipro.techbank.domain.Operation;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    private List<ClientDto> clients = new ArrayList<>();

    private List<CreditCardResponseDto> creditCards = new ArrayList<>();

    private List<OperationResponseDto> operations = new ArrayList<>();

    public CheckingAccountDto(CheckingAccount checkingAccount) {
        id = checkingAccount.getId();
        balance = checkingAccount.getBalance();
    }

    public CheckingAccountDto(CheckingAccount entity, List<Client> clients) {
        this(entity);
        clients.forEach(client -> this.clients.add(new ClientDto(client)));
    }
}
