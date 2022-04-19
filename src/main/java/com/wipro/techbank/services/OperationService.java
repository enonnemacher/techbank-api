package com.wipro.techbank.services;

import com.wipro.techbank.domain.Operation;

public interface OperationService {

    Operation salve(Operation operation);
    void withDraw(Long number, Double value);
    void deposit(Long number, Double value);
    void transfer(Long number, Long NumberAccountDest, Double value);
}
