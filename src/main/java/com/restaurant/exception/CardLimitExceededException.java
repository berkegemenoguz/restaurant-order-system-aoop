package com.restaurant.exception;

/**
 * Thrown when the order total is above the available credit card limit.
 */
public class CardLimitExceededException extends PaymentException {

    public CardLimitExceededException(String message) {
        super(message);
    }
}
