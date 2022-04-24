package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_account")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account  implements Serializable {
    private static final long serialVersionUID = -6666350505838863149L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    public void deposit(Double value) {
        this.balance += value;
    }

}