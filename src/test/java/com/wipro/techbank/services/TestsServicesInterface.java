package com.wipro.techbank.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public interface TestsServicesInterface {

    void setUp();

    void findAllShouldReturnPage();

    void findByIdShouldReturnCreditCardResponseDtoWhenIdExixts();

    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts();

    void createShouldReturnCreditCardResponseDto();

    void deleteShouldThrowDataBasesExceptionWhenIdIsDependent();

    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists();

    void deleteShouldReturnNothingWhenIdExists();
}
