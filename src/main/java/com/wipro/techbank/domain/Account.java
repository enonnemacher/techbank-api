package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account  implements Serializable {
    private static final long serialVersionUID = -6666350505838863149L;

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private Double balance;

    @OneToOne
    @JoinColumn(nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(nullable = false)
    private CreditCard creditCard;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

}