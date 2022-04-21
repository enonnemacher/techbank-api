package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.SpecialAccount;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecialAccountDto {

    private static final long serialVersionUID = -8488733565017924592L;

    @EqualsAndHashCode.Include
    private Long id;

    private Double balance;

    private Double creditSpecial;

    private Double creditSpecialUsed;
    private List<ClientDto> clients = new ArrayList<>();

    private List<CreditCardResponseDto> creditCards = new ArrayList<>();

    private List<TransactionResponseDto> operations = new ArrayList<>();


    public SpecialAccountDto(SpecialAccount specialAccount) {
        id = specialAccount.getId();
        balance = specialAccount.getBalance();
        creditSpecial = specialAccount.getCreditSpecial();
        creditSpecialUsed = specialAccount.getCreditSpecialUsed();
    }

    public SpecialAccountDto(SpecialAccount entity, List<Client> clients) {
        this(entity);
        clients.forEach(client -> this.clients.add(new ClientDto(client)));
    }

}
