package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tb_checking_account")
public class CheckingAccount extends Account  implements Serializable {
    private static final long serialVersionUID = 9101340754629012324L;


}
