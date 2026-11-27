package com.restaurant.menu;

import com.restaurant.exception.InvalidMenuItemException;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

/**
 * Creates menu items (<b>Factory</b> pattern).
 * <p>
 * The factory hides which concrete {@link MenuItem} subclass is created and
 * gives every new item a unique id with a category prefix
 * ({@code F-1}, {@code D-1}, {@code S-1}, ...). Client code only depends on
 * the {@link MenuItem} abstraction and the {@link MenuCategory} it asks for.
 */
public class MenuItemFactory {

    /** Default preparation time of a food created with {@link #create}. */
    public static final int DEFAULT_PREPARATION_MINUTES = 15;

    /** Default volume of a drink created with {@link #create}. */
    public static final int DEFAULT_DRINK_VOLUME_ML = 330;

    private final Map<MenuCategory, Integer> counters = new EnumMap<>(MenuCategory.class);

    /**
     * Creates an item of the given category with the default attributes of that
     * category: a non-vegetarian food, a non-alcoholic drink or a dessert
     * without ice cream.
     *
     * @throws InvalidMenuItemException if the category is null or the item data is invalid
     */
    public MenuItem create(MenuCategory category, String name, BigDecimal price) {
        if (category == null) {
            throw new InvalidMenuItemException("Menu item category must not be null");
        }
        return switch (category) {
            case FOOD -> createFood(name, price, DEFAULT_PREPARATION_MINUTES, false);
            case DRINK -> createDrink(name, price, DEFAULT_DRINK_VOLUME_ML, false);
            case DESSERT -> createDessert(name, price, false);
        };
    }

    /**
     * Creates a food with a new id.
     */
    public Food createFood(String name, BigDecimal price, int preparationMinutes, boolean vegetarian) {
        return new Food(nextId(MenuCategory.FOOD), name, price, preparationMinutes, vegetarian);
    }

    /**
     * Creates a drink with a new id.
     */
    public Drink createDrink(String name, BigDecimal price, int volumeMl, boolean alcoholic) {
        return new Drink(nextId(MenuCategory.DRINK), name, price, volumeMl, alcoholic);
    }

    /**
     * Creates a dessert with a new id.
     */
    public Dessert createDessert(String name, BigDecimal price, boolean withIceCream) {
        return new Dessert(nextId(MenuCategory.DESSERT), name, price, withIceCream);
    }

    private String nextId(MenuCategory category) {
        int next = counters.merge(category, 1, Integer::sum);
        return prefixOf(category) + "-" + next;
    }

    private static String prefixOf(MenuCategory category) {
        return switch (category) {
            case FOOD -> "F";
            case DRINK -> "D";
            case DESSERT -> "S";
        };
    }
}
