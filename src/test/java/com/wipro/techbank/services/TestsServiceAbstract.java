package com.wipro.techbank.services;

import lombok.Getter;

@Getter
public abstract class TestsServiceAbstract implements TestsServicesInterface{

    private Long existsId;
    private Long nonExistsId;
    private Long dependentId;

    private final Integer sizeLists = 10;

    @Override
    public void setUp() {
        existsId = 1L;
        nonExistsId = 2L;
        dependentId = 3L;
    }
}
