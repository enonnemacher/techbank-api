package com.wipro.techbank.services.serviceImpl;

import com.wipro.techbank.domain.SpecialAccount;
import com.wipro.techbank.repositories.SpecialAccountRepository;
import com.wipro.techbank.services.SpecialAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SpecialAccountImplements implements SpecialAccountService {

    @Autowired
    private SpecialAccountRepository specialAccountRepository;

    @Override
    public List<SpecialAccount> findAll() {
        return specialAccountRepository.findAll();
    }

    @Override
    public SpecialAccount save(SpecialAccount specialAccount) {
        return specialAccountRepository.save(specialAccount);
    }

    @Override
    public SpecialAccount findByNumber(Long number) {
        Optional<SpecialAccount> account = specialAccountRepository.findById(number);
        return account.get();
    }

    @Override
    public SpecialAccount update(Long number, SpecialAccount specialAccount) {
        return specialAccount;
    }

    @Override
    public void delete(Long number) {
        specialAccountRepository.deleteById(number);
    }
}
