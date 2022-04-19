package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@MappedSuperclass
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long number = 0L;
    protected Double balance;
    private Client client;
    private CreditCard creditCard;


//    public void deposit(Double value){
//        this.balance+= value;
//    }
//
//    public abstract void withDraw(Double value);
//
//    public abstract void transfer(Double value, Account account);
}
