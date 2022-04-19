package com.wipro.techbank.services;


import com.wipro.techbank.domain.SpecialAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecialAccountService {

    List<SpecialAccount> findAll();
    SpecialAccount save(SpecialAccount specialAccount);
    SpecialAccount findByNumber(Long number);
    SpecialAccount update (Long number, SpecialAccount specialAccount);
    void delete(Long number);

}
