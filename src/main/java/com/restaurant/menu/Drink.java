package com.restaurant.menu;

import com.restaurant.exception.InvalidMenuItemException;

import java.math.BigDecimal;

/**
 * A drink. Alcoholic drinks carry a special consumption tax of
 * {@link #ALCOHOL_TAX_RATE} on top of the base price.
 */
public class Drink extends MenuItem {

    /** Special consumption tax rate applied to alcoholic drinks (20%). */
    public static final BigDecimal ALCOHOL_TAX_RATE = new BigDecimal("0.20");

    private final int volumeMl;
    private final boolean alcoholic;

    public Drink(String id, String name, BigDecimal basePrice, int volumeMl, boolean alcoholic) {
        super(id, name, basePrice);
        if (volumeMl <= 0) {
            throw new InvalidMenuItemException("Drink volume must be greater than zero, but was " + volumeMl);
        }
        this.volumeMl = volumeMl;
        this.alcoholic = alcoholic;
    }

    @Override
    protected BigDecimal extraCharge() {
        return alcoholic ? getBasePrice().multiply(ALCOHOL_TAX_RATE) : BigDecimal.ZERO;
    }

    @Override
    public MenuCategory getCategory() {
        return MenuCategory.DRINK;
    }

    /**
     * Alcoholic drinks may only be ordered by adult customers.
     */
    @Override
    public boolean isAgeRestricted() {
        return alcoholic;
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }
}
