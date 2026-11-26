package com.restaurant.menu;

import com.restaurant.exception.InvalidMenuItemException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DrinkTest {

    @Test
    void nonAlcoholicDrinkPriceEqualsBasePrice() {
        Drink cola = new Drink("D1", "Cola", new BigDecimal("40.00"), 330, false);
        assertEquals(new BigDecimal("40.00"), cola.getPrice());
    }

    @Test
    void alcoholicDrinkPriceIncludesAlcoholTax() {
        Drink beer = new Drink("D2", "Beer", new BigDecimal("100.00"), 500, true);
        assertEquals(new BigDecimal("120.00"), beer.getPrice());
    }

    @Test
    void drinkCategoryIsDrink() {
        assertEquals(MenuCategory.DRINK, new Drink("D1", "Cola", BigDecimal.TEN, 330, false).getCategory());
    }

    @Test
    void zeroVolumeThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> new Drink("D1", "Cola", BigDecimal.TEN, 0, false));
    }
}
