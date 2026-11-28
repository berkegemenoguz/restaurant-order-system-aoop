package com.restaurant.exception;

/**
 * Thrown when an order is created with invalid data, such as a blank id or a missing order date.
 */
public class InvalidOrderException extends RestaurantException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
