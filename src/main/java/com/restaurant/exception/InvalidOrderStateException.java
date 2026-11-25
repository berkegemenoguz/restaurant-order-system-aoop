package com.restaurant.exception;

/**
 * Thrown when an operation is not allowed in the current state of the order (e.g. adding an item to a paid order).
 */
public class InvalidOrderStateException extends RestaurantException {

    public InvalidOrderStateException(String message) {
        super(message);
    }
}
