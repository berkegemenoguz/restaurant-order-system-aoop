package com.restaurant.exception;

/**
 * Thrown when a credit card is expired on the payment date.
 */
public class CardExpiredException extends PaymentException {

    public CardExpiredException(String message) {
        super(message);
    }
}
