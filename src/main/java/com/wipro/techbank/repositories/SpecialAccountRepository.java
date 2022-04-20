package com.wipro.techbank.repositories;

import com.wipro.techbank.domain.SpecialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialAccountRepository extends JpaRepository<SpecialAccount,Long> {

}
