package com.wipro.techbank.services;


import com.wipro.techbank.domain.CheckingAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CheckingAccountService {

    List<CheckingAccount> findAll();
    CheckingAccount save(CheckingAccount checkingAccount);
    CheckingAccount findByNumber(Long number);
    void update (Long number, CheckingAccount checkingAccount);
    void delete(Long number);

}
