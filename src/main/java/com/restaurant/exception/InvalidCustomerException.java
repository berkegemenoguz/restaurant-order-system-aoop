package com.restaurant.exception;

/**
 * Thrown when a customer is created with invalid data, such as a blank name or a missing birth date.
 */
public class InvalidCustomerException extends RestaurantException {

    public InvalidCustomerException(String message) {
        super(message);
    }
}
