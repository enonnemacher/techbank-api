package com.wipro.techbank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_special_account")
public class SpecialAccount extends Account  implements Serializable {
    private static final long serialVersionUID = -2401085526099065807L;

    @OneToOne
    @JoinColumn(nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(nullable = false)
    private CreditCard creditCard;

    private Double creditSpecial;

    private Double creditSpecialUsed = 0.0;
}