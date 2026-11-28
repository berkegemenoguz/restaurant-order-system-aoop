package com.restaurant.customer;

import com.restaurant.exception.InvalidCustomerException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * A customer of the restaurant. Two customers are equal when they have the same id.
 */
public class Customer {

    /** The minimum age to order age restricted items. */
    public static final int LEGAL_AGE = 18;

    private final String id;
    private final String name;
    private final LocalDate birthDate;

    public Customer(String id, String name, LocalDate birthDate) {
        if (id == null || id.isBlank()) {
            throw new InvalidCustomerException("Customer id must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidCustomerException("Customer name must not be blank");
        }
        if (birthDate == null) {
            throw new InvalidCustomerException("Customer birth date must not be null");
        }
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    /**
     * Returns the age of the customer in full years on the given date.
     *
     * @throws InvalidCustomerException if the date is null or before the birth date
     */
    public int getAgeOn(LocalDate date) {
        if (date == null) {
            throw new InvalidCustomerException("Date must not be null");
        }
        if (date.isBefore(birthDate)) {
            throw new InvalidCustomerException("Date " + date + " is before the birth date " + birthDate);
        }
        return Period.between(birthDate, date).getYears();
    }

    /**
     * Returns whether the customer has reached {@link #LEGAL_AGE} on the given date.
     */
    public boolean isAdultOn(LocalDate date) {
        return getAgeOn(date) >= LEGAL_AGE;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer other)) {
            return false;
        }
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
