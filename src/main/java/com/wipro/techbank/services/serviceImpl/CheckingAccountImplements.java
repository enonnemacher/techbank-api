package com.wipro.techbank.services.serviceImpl;

import com.wipro.techbank.domain.CheckingAccount;
import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.repositories.CheckingAccountRepository;
import com.wipro.techbank.services.CheckingAccountService;
import com.wipro.techbank.services.SpecialAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CheckingAccountImplements implements CheckingAccountService {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Override
    public List<CheckingAccount> findAll() {
        return checkingAccountRepository.findAll();
    }

    @Override
    public CheckingAccount save(CheckingAccount checkingAccount) {
        return checkingAccountRepository.save(checkingAccount);
    }

    @Override
    public CheckingAccount findByNumber(Long number) {
        Optional<CheckingAccount> account = checkingAccountRepository.findById(number);
        return account.get();
    }

    @Override
    public void update(Long number, CheckingAccount checkingAccount) {
        CheckingAccount account = checkingAccountRepository.findById(number).get();
        BeanUtils.copyProperties(checkingAccount, account);

    }

    @Override
    public void delete(Long number) {
        checkingAccountRepository.deleteById(number);
    }
}
