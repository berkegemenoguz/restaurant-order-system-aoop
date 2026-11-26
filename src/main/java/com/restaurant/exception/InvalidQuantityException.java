package com.restaurant.exception;

/**
 * Thrown when the quantity of an order line is zero or negative.
 */
public class InvalidQuantityException extends RestaurantException {

    public InvalidQuantityException(String message) {
        super(message);
    }
}
