package com.wipro.techbank.services.exceptions;

public class ValueNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = -4306896249566152588L;

    public ValueNotAllowedException(String msg) {
        super(msg);
    }
}
