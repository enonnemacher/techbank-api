package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_checking_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CheckingAccount extends Account  implements Serializable {
    private static final long serialVersionUID = 9101340754629012324L;

    @ManyToMany
    @JoinTable(name = "tb_checking_account_client",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> clients = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tb_checking_account_credit_card",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "credit_card_id"))
    private List<CreditCard> creditCards = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "tb_checking_account_operation",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id"))
    private List<Transaction> transactions = new ArrayList<>();

}
