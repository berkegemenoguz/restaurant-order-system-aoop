package com.restaurant.menu;

import com.restaurant.exception.InvalidMenuItemException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Base class of every item that can be ordered from the menu.
 * <p>
 * The final price is calculated with the <b>Template Method</b> pattern:
 * {@link #getPrice()} is fixed here and adds the type specific
 * {@link #extraCharge()} of each subclass to the base price. New item types
 * can be added without changing this class (Open/Closed Principle).
 * <p>
 * Two menu items are equal when they have the same id.
 */
public abstract class MenuItem {

    private final String id;
    private final String name;
    private final BigDecimal basePrice;

    protected MenuItem(String id, String name, BigDecimal basePrice) {
        this.id = requireText(id, "id");
        this.name = requireText(name, "name");
        this.basePrice = requirePositive(basePrice);
    }

    /**
     * Returns the price a customer pays for one unit of this item:
     * the base price plus the extra charge of the item type, rounded to 2 decimals.
     */
    public final BigDecimal getPrice() {
        return basePrice.add(extraCharge()).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * The extra amount added to the base price by the item type.
     * Must never be negative.
     */
    protected abstract BigDecimal extraCharge();

    /**
     * The category of this item on the menu.
     */
    public abstract MenuCategory getCategory();

    /**
     * Returns whether only adult customers may order this item.
     * Items are not age restricted unless a subclass says otherwise.
     */
    public boolean isAgeRestricted() {
        return false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidMenuItemException("Menu item " + field + " must not be blank");
        }
        return value;
    }

    private static BigDecimal requirePositive(BigDecimal price) {
        if (price == null || price.signum() <= 0) {
            throw new InvalidMenuItemException("Menu item price must be greater than zero, but was " + price);
        }
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItem other)) {
            return false;
        }
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getCategory() + "[" + id + ", " + name + ", " + getPrice() + "]";
    }
}
