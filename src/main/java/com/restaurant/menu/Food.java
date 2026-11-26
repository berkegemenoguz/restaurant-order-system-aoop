package com.restaurant.menu;

import com.restaurant.exception.InvalidMenuItemException;

import java.math.BigDecimal;

/**
 * A main dish or starter. Food has no extra charge; it has a preparation time
 * and can be vegetarian.
 */
public class Food extends MenuItem {

    private final int preparationMinutes;
    private final boolean vegetarian;

    public Food(String id, String name, BigDecimal basePrice, int preparationMinutes, boolean vegetarian) {
        super(id, name, basePrice);
        if (preparationMinutes <= 0) {
            throw new InvalidMenuItemException(
                    "Preparation time must be greater than zero, but was " + preparationMinutes);
        }
        this.preparationMinutes = preparationMinutes;
        this.vegetarian = vegetarian;
    }

    @Override
    protected BigDecimal extraCharge() {
        return BigDecimal.ZERO;
    }

    @Override
    public MenuCategory getCategory() {
        return MenuCategory.FOOD;
    }

    public int getPreparationMinutes() {
        return preparationMinutes;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }
}
