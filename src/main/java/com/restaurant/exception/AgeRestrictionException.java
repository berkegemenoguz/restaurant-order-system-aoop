package com.restaurant.exception;

/**
 * Thrown when an age restricted item (an alcoholic drink) is added to the order of a customer under the legal age.
 */
public class AgeRestrictionException extends RestaurantException {

    public AgeRestrictionException(String message) {
        super(message);
    }
}
