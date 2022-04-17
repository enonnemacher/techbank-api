package com.wipro.techbank.domain;

import java.util.Date;

public class CreditCard {

    private Long id;
    private String cardNumber;
    private Date expirationDate;
    private Short securityCode;
    private Double limit;
    private Double usedLimit = 0d;
}
