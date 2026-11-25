package com.restaurant.exception;

/**
 * Thrown when a menu item with the same id is added to the menu twice.
 */
public class DuplicateMenuItemException extends RestaurantException {

    public DuplicateMenuItemException(String message) {
        super(message);
    }
}
