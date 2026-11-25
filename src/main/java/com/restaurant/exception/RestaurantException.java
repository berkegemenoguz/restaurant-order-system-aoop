package com.restaurant.exception;

/**
 * Base exception of the restaurant ordering domain.
 * <p>
 * Every business rule violation in this project is reported with a subclass of
 * this exception. It is unchecked because the caller cannot usually recover from
 * a broken business rule at the call site; the message explains what went wrong.
 */
public class RestaurantException extends RuntimeException {

    public RestaurantException(String message) {
        super(message);
    }

    public RestaurantException(String message, Throwable cause) {
        super(message, cause);
    }
}
