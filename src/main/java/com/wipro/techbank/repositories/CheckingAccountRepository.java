package com.wipro.techbank.repositories;

import com.wipro.techbank.domain.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
}
