package com.restaurant.exception;

/**
 * Thrown when a menu item is created with invalid data, such as a blank name,
 * a price that is not positive or a non-positive volume for a drink.
 */
public class InvalidMenuItemException extends RestaurantException {

    public InvalidMenuItemException(String message) {
        super(message);
    }
}
