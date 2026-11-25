package com.restaurant.exception;

/**
 * Thrown when the given cash amount does not cover the order total.
 */
public class InsufficientPaymentException extends PaymentException {

    public InsufficientPaymentException(String message) {
        super(message);
    }
}
