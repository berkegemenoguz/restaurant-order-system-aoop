package com.restaurant.exception;

/**
 * Thrown when a quantity or price is zero or negative.
 */
public class InvalidQuantityException extends RestaurantException {

    public InvalidQuantityException(String message) {
        super(message);
    }
}
