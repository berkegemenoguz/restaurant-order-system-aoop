package com.restaurant.menu;

import java.math.BigDecimal;

/**
 * A dessert. A dessert can be served with a scoop of ice cream, which adds
 * {@link #ICE_CREAM_PRICE} to the base price.
 */
public class Dessert extends MenuItem {

    /** Extra price of the ice cream served with a dessert. */
    public static final BigDecimal ICE_CREAM_PRICE = new BigDecimal("15.00");

    private final boolean withIceCream;

    public Dessert(String id, String name, BigDecimal basePrice, boolean withIceCream) {
        super(id, name, basePrice);
        this.withIceCream = withIceCream;
    }

    @Override
    protected BigDecimal extraCharge() {
        return withIceCream ? ICE_CREAM_PRICE : BigDecimal.ZERO;
    }

    @Override
    public MenuCategory getCategory() {
        return MenuCategory.DESSERT;
    }

    public boolean isWithIceCream() {
        return withIceCream;
    }
}
