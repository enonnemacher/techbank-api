package com.wipro.techbank.services.serviceImpl;

import com.wipro.techbank.domain.Account;
import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.repositories.OperationRepository;
import com.wipro.techbank.services.CheckingAccountService;
import com.wipro.techbank.services.OperationService;
import com.wipro.techbank.services.SpecialAccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class OperationServiceImplements implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private SpecialAccountService specialAccountService;

    @Autowired
    CheckingAccountService checkingAccountService;

    @Override
    public Operation salve(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public void withDraw(Long number, Double value) {

        if(specialAccountService.findByNumber(number).getClass().equals(SpecialAccount.class)) {

            SpecialAccount account = specialAccountService.findByNumber(number);

            if (value <= account.getBalance()) {
                account.setBalance(account.getBalance() - value);
            } else if (value <= (account.getBalance() + account.getLimit())) {
                account.setUsedLimit(account.getBalance() - value);
                account.setBalance(account.getUsedLimit() * -1);
            } else {
                throw new RuntimeException("Saldo Insuficiente");
            }
        }
        else {
            CheckingAccount account = checkingAccountService.findByNumber(number);
            account.setBalance(account.getBalance() - value);
        }
    }

    @Override
    public void deposit(Long number, Double value) {
        Account account;
        if(specialAccountService.findByNumber(number).equals(SpecialAccount.class)){
        account = specialAccountService.findByNumber(number);
        }else
            account = checkingAccountService.findByNumber(number);

        account.setBalance(value);
    }

    @Override
    public void transfer(Long number, Long NumberAccountDest, Double value) {

    }
}
