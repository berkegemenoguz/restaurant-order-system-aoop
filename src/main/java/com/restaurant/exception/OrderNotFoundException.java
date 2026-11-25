package com.restaurant.exception;

/**
 * Thrown when an order with the requested id does not exist.
 */
public class OrderNotFoundException extends RestaurantException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
