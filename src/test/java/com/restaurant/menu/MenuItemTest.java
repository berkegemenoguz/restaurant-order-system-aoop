package com.restaurant.menu;

import com.restaurant.exception.InvalidMenuItemException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the rules shared by every menu item (validation, equality).
 * {@link Food} is used as a concrete type because {@link MenuItem} is abstract.
 */
class MenuItemTest {

    private static final BigDecimal PRICE = new BigDecimal("100.00");

    @Test
    void blankIdThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new Food(" ", "Burger", PRICE, 10, false));
    }

    @Test
    void nullIdThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new Food(null, "Burger", PRICE, 10, false));
    }

    @Test
    void blankNameThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new Food("F1", "", PRICE, 10, false));
    }

    @Test
    void zeroPriceThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new Food("F1", "Burger", BigDecimal.ZERO, 10, false));
    }

    @Test
    void negativePriceThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class,
                () -> new Food("F1", "Burger", new BigDecimal("-5"), 10, false));
    }

    @Test
    void nullPriceThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new Food("F1", "Burger", null, 10, false));
    }

    @Test
    void invalidPriceMessageContainsThePrice() {
        InvalidMenuItemException ex = assertThrows(InvalidMenuItemException.class,
                () -> new Food("F1", "Burger", new BigDecimal("-5"), 10, false));
        assertTrue(ex.getMessage().contains("-5"));
    }

    @Test
    void itemsWithSameIdAreEqual() {
        MenuItem a = new Food("F1", "Burger", PRICE, 10, false);
        MenuItem b = new Dessert("F1", "Cake", PRICE, false);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void itemsWithDifferentIdsAreNotEqual() {
        assertNotEquals(new Food("F1", "Burger", PRICE, 10, false), new Food("F2", "Burger", PRICE, 10, false));
    }

    @Test
    void priceIsRoundedToTwoDecimals() {
        MenuItem item = new Food("F1", "Burger", new BigDecimal("10.005"), 10, false);
        assertEquals(new BigDecimal("10.01"), item.getPrice());
    }
}
