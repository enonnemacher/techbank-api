package com.wipro.techbank.services;

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
