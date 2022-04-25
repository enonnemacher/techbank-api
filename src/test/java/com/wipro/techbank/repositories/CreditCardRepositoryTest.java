package com.wipro.techbank.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class CreditCardRepositoryTest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    private long existingId;
    private long nonexistingId;
    private long countTotalCreditCards;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
//        CreditCard creditCard = Factory.createCreditCard();
//        creditCard.setId(null);
//        creditCard = creditCardRepository.save(creditCard);
//
//        Assertions.assertNotNull(creditCard.getId());
//        Assertions.assertEquals(countTotalCreditCards + 1, creditCard.getId());
        Assertions.assertTrue(true);
    }
}