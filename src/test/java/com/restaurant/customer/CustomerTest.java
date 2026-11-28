package com.restaurant.customer;

import com.restaurant.exception.InvalidCustomerException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {

    private static final LocalDate BIRTH_DATE = LocalDate.of(2000, 6, 15);

    private final Customer customer = new Customer("C-1", "Ayse", BIRTH_DATE);

    @Test
    void blankIdThrowsInvalidCustomerException() {
        assertThrows(InvalidCustomerException.class, () -> new Customer("", "Ayse", BIRTH_DATE));
    }

    @Test
    void blankNameThrowsInvalidCustomerException() {
        assertThrows(InvalidCustomerException.class, () -> new Customer("C-1", " ", BIRTH_DATE));
    }

    @Test
    void nullBirthDateThrowsInvalidCustomerException() {
        assertThrows(InvalidCustomerException.class, () -> new Customer("C-1", "Ayse", null));
    }

    @Test
    void ageIsCountedInFullYears() {
        assertEquals(25, customer.getAgeOn(LocalDate.of(2026, 6, 14)));
    }

    @Test
    void ageIncreasesOnBirthday() {
        assertEquals(26, customer.getAgeOn(LocalDate.of(2026, 6, 15)));
    }

    @Test
    void ageOnDateBeforeBirthThrowsInvalidCustomerException() {
        assertThrows(InvalidCustomerException.class, () -> customer.getAgeOn(LocalDate.of(1999, 1, 1)));
    }

    @Test
    void ageOnNullDateThrowsInvalidCustomerException() {
        assertThrows(InvalidCustomerException.class, () -> customer.getAgeOn(null));
    }

    @Test
    void customerIsAdultOnEighteenthBirthday() {
        assertTrue(customer.isAdultOn(LocalDate.of(2018, 6, 15)));
    }

    @Test
    void customerIsNotAdultTheDayBeforeEighteenthBirthday() {
        assertFalse(customer.isAdultOn(LocalDate.of(2018, 6, 14)));
    }

    @Test
    void customersWithSameIdAreEqual() {
        assertEquals(customer, new Customer("C-1", "Other", LocalDate.of(1990, 1, 1)));
    }

    @Test
    void customersWithDifferentIdsAreNotEqual() {
        assertNotEquals(customer, new Customer("C-2", "Ayse", BIRTH_DATE));
    }
}
