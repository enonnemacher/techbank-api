package com.wipro.techbank.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private static Long number = 0L;
    protected Double balance;
    private Client client;
    private CreditCard creditCard;

    protected List<Operation> statement = new ArrayList<>();

    public void deposit(){}

    public abstract void withDraw();

    public abstract void transfer();
}
