package com.restaurant.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ExceptionHierarchyTest {

    @Test
    void baseExceptionIsUnchecked() {
        assertInstanceOf(RuntimeException.class, new RestaurantException("x"));
    }

    @Test
    void baseExceptionKeepsMessageAndCause() {
        IllegalStateException cause = new IllegalStateException("root");
        RestaurantException ex = new RestaurantException("failed", cause);
        assertEquals("failed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void domainExceptionsExtendBaseException() {
        assertInstanceOf(RestaurantException.class, new MenuItemNotFoundException("m"));
        assertInstanceOf(RestaurantException.class, new DuplicateMenuItemException("m"));
        assertInstanceOf(RestaurantException.class, new InvalidMenuItemException("m"));
        assertInstanceOf(RestaurantException.class, new InvalidQuantityException("m"));
        assertInstanceOf(RestaurantException.class, new OrderNotFoundException("m"));
        assertInstanceOf(RestaurantException.class, new ItemNotInOrderException("m"));
        assertInstanceOf(RestaurantException.class, new InvalidOrderStateException("m"));
        assertInstanceOf(RestaurantException.class, new EmptyOrderException("m"));
        assertInstanceOf(RestaurantException.class, new PaymentException("m"));
    }

    @Test
    void paymentExceptionsExtendPaymentException() {
        assertInstanceOf(PaymentException.class, new InsufficientPaymentException("m"));
        assertInstanceOf(PaymentException.class, new CardExpiredException("m"));
        assertInstanceOf(PaymentException.class, new CardLimitExceededException("m"));
    }
}
