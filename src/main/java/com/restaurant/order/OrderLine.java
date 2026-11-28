package com.restaurant.order;

import com.restaurant.exception.InvalidMenuItemException;
import com.restaurant.exception.InvalidQuantityException;
import com.restaurant.menu.MenuItem;

import java.math.BigDecimal;

/**
 * One line of an order: a menu item and how many of it were ordered.
 * <p>
 * An order line is immutable; changing the quantity returns a new line.
 */
public final class OrderLine {

    private final MenuItem item;
    private final int quantity;

    public OrderLine(MenuItem item, int quantity) {
        if (item == null) {
            throw new InvalidMenuItemException("Order line item must not be null");
        }
        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero, but was " + quantity);
        }
        this.item = item;
        this.quantity = quantity;
    }

    /**
     * Returns the price of this line: unit price times quantity.
     */
    public BigDecimal getLineTotal() {
        return item.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Returns a new line with the given quantity.
     *
     * @throws InvalidQuantityException if the quantity is not positive
     */
    public OrderLine withQuantity(int newQuantity) {
        return new OrderLine(item, newQuantity);
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
