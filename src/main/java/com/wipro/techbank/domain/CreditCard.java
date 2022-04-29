package com.wipro.techbank.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wipro.techbank.Utils.Utils;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

import static com.wipro.techbank.Utils.Utils.*;


@Entity
@Table(name = "tb_credit_card")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CreditCard implements Serializable {
    private static final long serialVersionUID = -8194331057711583877L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private Short securityCode;

    @Column(nullable = false)
    private Double limitCredit;

    private Double usedLimit;

    public CreditCard() {
        expirationDate = Utils.generateExpirationDate();
        securityCode = Short.valueOf(generateNumbers(LENGTH_SECURITY_CODE));
        cardNumber = generateNumbers(LENGTH_CARD_NUMBER);
        usedLimit = 0.0;
    }

    public CreditCard(Double limitCredit) {
        this();
        this.limitCredit = limitCredit;
    }

    public CreditCard(Long id, Double limitCredit) {
        this(limitCredit);
        this.id = id;
    }



}
