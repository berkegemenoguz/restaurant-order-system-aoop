package com.restaurant.menu;

import com.restaurant.exception.InvalidMenuItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuItemFactoryTest {

    private static final BigDecimal PRICE = new BigDecimal("50.00");

    private MenuItemFactory factory;

    @BeforeEach
    void setUp() {
        factory = new MenuItemFactory();
    }

    @Test
    void createWithFoodCategoryReturnsFood() {
        assertInstanceOf(Food.class, factory.create(MenuCategory.FOOD, "Burger", PRICE));
    }

    @Test
    void createWithDrinkCategoryReturnsDrink() {
        assertInstanceOf(Drink.class, factory.create(MenuCategory.DRINK, "Cola", PRICE));
    }

    @Test
    void createWithDessertCategoryReturnsDessert() {
        assertInstanceOf(Dessert.class, factory.create(MenuCategory.DESSERT, "Baklava", PRICE));
    }

    @Test
    void createWithNullCategoryThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> factory.create(null, "Burger", PRICE));
    }

    @Test
    void createWithInvalidPriceThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class,
                () -> factory.create(MenuCategory.FOOD, "Burger", BigDecimal.ZERO));
    }

    @Test
    void createdFoodHasDefaultAttributes() {
        Food food = (Food) factory.create(MenuCategory.FOOD, "Burger", PRICE);
        assertEquals(MenuItemFactory.DEFAULT_PREPARATION_MINUTES, food.getPreparationMinutes());
        assertFalse(food.isVegetarian());
    }

    @Test
    void createdDrinkIsNonAlcoholicByDefault() {
        Drink drink = (Drink) factory.create(MenuCategory.DRINK, "Cola", PRICE);
        assertFalse(drink.isAlcoholic());
        assertEquals(MenuItemFactory.DEFAULT_DRINK_VOLUME_ML, drink.getVolumeMl());
    }

    @Test
    void createdDessertHasNoIceCreamByDefault() {
        Dessert dessert = (Dessert) factory.create(MenuCategory.DESSERT, "Baklava", PRICE);
        assertFalse(dessert.isWithIceCream());
    }

    @Test
    void idsHaveCategoryPrefix() {
        assertTrue(factory.create(MenuCategory.FOOD, "Burger", PRICE).getId().startsWith("F-"));
        assertTrue(factory.create(MenuCategory.DRINK, "Cola", PRICE).getId().startsWith("D-"));
        assertTrue(factory.create(MenuCategory.DESSERT, "Cake", PRICE).getId().startsWith("S-"));
    }

    @Test
    void consecutiveItemsGetDifferentIds() {
        MenuItem first = factory.createFood("Burger", PRICE, 10, false);
        MenuItem second = factory.createFood("Pizza", PRICE, 20, false);
        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    void createDrinkKeepsGivenAttributes() {
        Drink beer = factory.createDrink("Beer", PRICE, 500, true);
        assertTrue(beer.isAlcoholic());
        assertEquals(500, beer.getVolumeMl());
    }

    @Test
    void createDessertKeepsIceCreamOption() {
        assertTrue(factory.createDessert("Baklava", PRICE, true).isWithIceCream());
    }
}
