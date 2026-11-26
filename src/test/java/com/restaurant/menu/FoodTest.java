package com.restaurant.menu;

import com.restaurant.exception.InvalidMenuItemException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FoodTest {

    @Test
    void foodPriceEqualsBasePrice() {
        Food food = new Food("F1", "Burger", new BigDecimal("180.00"), 15, false);
        assertEquals(new BigDecimal("180.00"), food.getPrice());
    }

    @Test
    void foodCategoryIsFood() {
        assertEquals(MenuCategory.FOOD, new Food("F1", "Burger", BigDecimal.TEN, 15, false).getCategory());
    }

    @Test
    void zeroPreparationTimeThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new Food("F1", "Burger", BigDecimal.TEN, 0, false));
    }

    @Test
    void negativePreparationTimeThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new Food("F1", "Burger", BigDecimal.TEN, -3, true));
    }
}
