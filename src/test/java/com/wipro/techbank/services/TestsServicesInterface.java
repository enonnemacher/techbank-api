package com.wipro.techbank.services;

public interface TestsServicesInterface {

    void setUp();

    void findAllShouldReturnPage();

    void findByIdShouldReturnDtoWhenIdExixts();

    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts();

    void createShouldReturnDto();

    void deleteShouldThrowDataBasesExceptionWhenIdIsDependent();

    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists();

    void deleteShouldReturnNothingWhenIdExists();
}
