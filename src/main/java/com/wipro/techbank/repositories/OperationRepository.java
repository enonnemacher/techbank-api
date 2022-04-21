package com.wipro.techbank.repositories;

import com.wipro.techbank.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Transaction, Long > {
}
