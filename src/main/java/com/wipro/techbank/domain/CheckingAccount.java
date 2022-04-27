package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "tb_checking_account")
public class CheckingAccount extends Account  implements Serializable {
    private static final long serialVersionUID = 9101340754629012324L;

    @OneToOne
    @JoinColumn(nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(nullable = false)
    private CreditCard creditCard;

    public CheckingAccount(Double balance, Client client, CreditCard creditCard) {
        super.setBalance(balance);
        this.client = client;
        this.creditCard = creditCard;
    }
}
