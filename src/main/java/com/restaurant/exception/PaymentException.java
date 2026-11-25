package com.restaurant.exception;

/**
 * Base exception for every payment failure.
 */
public class PaymentException extends RestaurantException {

    public PaymentException(String message) {
        super(message);
    }
}
