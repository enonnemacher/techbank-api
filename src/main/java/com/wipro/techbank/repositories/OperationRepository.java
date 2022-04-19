package com.wipro.techbank.repositories;


import com.wipro.techbank.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long > {
    List<Operation> findByAccountNumber(Long number);


}
