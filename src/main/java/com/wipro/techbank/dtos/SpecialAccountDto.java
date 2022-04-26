package com.wipro.techbank.dtos;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.domain.CreditCard;
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

    private Client client;
    private Double balance;

    private Double creditSpecial;

    private CreditCard creditCard;

//    private Double creditSpecialUsed;


    public SpecialAccountDto(SpecialAccount specialAccount) {
        id = specialAccount.getId();
        client = specialAccount.getClient();
        balance = specialAccount.getBalance();
        creditSpecial = specialAccount.getCreditSpecial();
        creditCard = specialAccount.getCreditCard();

//        creditSpecialUsed = specialAccount.getCreditSpecialUsed();
    }

//    public SpecialAccountDto(SpecialAccount entity, List<Client> clients) {
//        this(entity);
//        clients.forEach(client -> this.clients.add(new ClientDto(client)));
//    }

}
