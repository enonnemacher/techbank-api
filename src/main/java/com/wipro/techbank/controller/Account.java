package com.wipro.techbank.controller;

import java.util.ArrayList;

public abstract class Account {

    private static Long number = 0L;
    protected Double balance;
    private Client client;
    private CreditCard creditCard;

    protected List<Operation> statement = new ArrayList<>();
}
