package com.restaurant.menu;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DessertTest {

    @Test
    void dessertWithoutIceCreamPriceEqualsBasePrice() {
        Dessert baklava = new Dessert("S1", "Baklava", new BigDecimal("90.00"), false);
        assertEquals(new BigDecimal("90.00"), baklava.getPrice());
    }

    @Test
    void dessertWithIceCreamPriceIncludesIceCreamPrice() {
        Dessert baklava = new Dessert("S1", "Baklava", new BigDecimal("90.00"), true);
        assertEquals(new BigDecimal("105.00"), baklava.getPrice());
    }

    @Test
    void dessertCategoryIsDessert() {
        assertEquals(MenuCategory.DESSERT, new Dessert("S1", "Baklava", BigDecimal.TEN, false).getCategory());
    }
}
