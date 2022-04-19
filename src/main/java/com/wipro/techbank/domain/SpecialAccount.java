package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_special_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class SpecialAccount extends Account {

    @Column(nullable = false)
    private Double limit;

    private Double usedLimit;

//    @Override
//    public void withDraw(Double value) {
//        if (value <= this.getBalance()) {
//
//            this.balance -= value;
//
//        } else if (value <= (this.getBalance() + this.getLimit())) {
//
//            this.usedLimit = this.balance - value;
//            this.balance = this.usedLimit;
//
//        } else {
//            throw new RuntimeException("Saldo Insuficiente");
//        }
//    }
//
//    @Override
//    public void transfer(Double value, Account account) {
//        if (value <= (this.getBalance() + this.getLimit())) {
//            this.withDraw(value);
//            account.deposit(value);
//        }
//    }
}
