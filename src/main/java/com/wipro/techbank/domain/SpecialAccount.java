package com.wipro.techbank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tb_special_account")
public class SpecialAccount extends Account  implements Serializable {
    private static final long serialVersionUID = -2401085526099065807L;

    private Double creditSpecial;
    private Double creditSpecialUsed = 0.0;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "tb_special_account_client",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> clients = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "tb_special_account_credit_card",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "credit_card_id"))
    private List<CreditCard> creditCards = new ArrayList<>();


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "tb_special_account_operation",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id"))
    private List<Operation> operations = new ArrayList<>();
}