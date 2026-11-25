package com.restaurant.exception;

/**
 * Thrown when a menu item with the requested id does not exist on the menu.
 */
public class MenuItemNotFoundException extends RestaurantException {

    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
