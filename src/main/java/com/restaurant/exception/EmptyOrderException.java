package com.restaurant.exception;

/**
 * Thrown when an order without any items is paid.
 */
public class EmptyOrderException extends RestaurantException {

    public EmptyOrderException(String message) {
        super(message);
    }
}
