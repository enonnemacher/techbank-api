package com.wipro.techbank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.wipro.techbank.domain.Operation.WITHDRAW;


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
    @Override
    public void withdraw(Double value) {
        if (value <= this.getBalance()) {
            this.setBalance(this.getBalance() - value);

        } else if ((this.getBalance() + this.getCreditSpecial()) >= value) {
            this.setCreditSpecialUsed((this.getBalance() - value) * (-1));
            Double newBalance =  (this.getCreditSpecialUsed() * (-1));
            this.setBalance(newBalance);

        }else {
            throw new ResourceNotFoundException("Saldo insuficiente para realizar a operação");
        }
    }

    @Override
    public void deposit(Double value) {

        if(this.getCreditSpecialUsed() <= 0d) {
            this.setBalance(this.getBalance() + value);
        }else{
            Double valueToDeposit = this.getCreditSpecialUsed() - value;
            this.setCreditSpecialUsed(this.getCreditSpecialUsed() - value);
            if(valueToDeposit >= 0){
                this.setBalance(this.getBalance() + (valueToDeposit));
            }
        }
    }
}