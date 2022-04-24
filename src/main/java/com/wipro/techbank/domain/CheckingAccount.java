package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_checking_account")
@ToString
public class CheckingAccount extends Account  implements Serializable {
    private static final long serialVersionUID = 9101340754629012324L;

    @OneToOne
    @JoinColumn(nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(nullable = false)
    private CreditCard creditCard;
}
