package com.restaurant.exception;

/**
 * Thrown when removing an item that is not part of the order.
 */
public class ItemNotInOrderException extends RestaurantException {

    public ItemNotInOrderException(String message) {
        super(message);
    }
}
