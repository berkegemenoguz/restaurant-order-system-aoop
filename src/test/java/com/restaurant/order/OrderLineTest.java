package com.restaurant.order;

import com.restaurant.exception.InvalidMenuItemException;
import com.restaurant.exception.InvalidQuantityException;
import com.restaurant.menu.Food;
import com.restaurant.menu.MenuItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderLineTest {

    private final MenuItem burger = new Food("F-1", "Burger", new BigDecimal("180.00"), 15, false);

    @Test
    void lineTotalIsUnitPriceTimesQuantity() {
        assertEquals(new BigDecimal("540.00"), new OrderLine(burger, 3).getLineTotal());
    }

    @Test
    void zeroQuantityThrowsInvalidQuantityException() {
        assertThrows(InvalidQuantityException.class, () -> new OrderLine(burger, 0));
    }

    @Test
    void negativeQuantityThrowsInvalidQuantityException() {
        assertThrows(InvalidQuantityException.class, () -> new OrderLine(burger, -2));
    }

    @Test
    void nullItemThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new OrderLine(null, 1));
    }

    @Test
    void withQuantityReturnsLineWithNewQuantity() {
        OrderLine line = new OrderLine(burger, 1).withQuantity(4);
        assertEquals(4, line.getQuantity());
    }

    @Test
    void withQuantityDoesNotChangeOriginalLine() {
        OrderLine original = new OrderLine(burger, 1);
        original.withQuantity(4);
        assertEquals(1, original.getQuantity());
    }
}
