package com.restaurant.order;

import com.restaurant.customer.Customer;
import com.restaurant.exception.AgeRestrictionException;
import com.restaurant.exception.InvalidCustomerException;
import com.restaurant.exception.InvalidMenuItemException;
import com.restaurant.exception.InvalidOrderException;
import com.restaurant.exception.InvalidQuantityException;
import com.restaurant.exception.ItemNotInOrderException;
import com.restaurant.menu.MenuItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * An order of a customer. An order holds one {@link OrderLine} per menu item;
 * adding the same item again increases the quantity of its line.
 * <p>
 * The order date is given as a parameter; the system clock is never read.
 */
public class Order {

    private final String id;
    private final Customer customer;
    private final LocalDate orderDate;
    private final Map<String, OrderLine> lines = new LinkedHashMap<>();

    public Order(String id, Customer customer, LocalDate orderDate) {
        if (id == null || id.isBlank()) {
            throw new InvalidOrderException("Order id must not be blank");
        }
        if (customer == null) {
            throw new InvalidCustomerException("Order customer must not be null");
        }
        if (orderDate == null) {
            throw new InvalidOrderException("Order date must not be null");
        }
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
    }

    /**
     * Adds the given quantity of an item to the order.
     *
     * @throws InvalidMenuItemException if the item is null
     * @throws InvalidQuantityException if the quantity is not positive
     * @throws AgeRestrictionException  if the item is age restricted and the customer
     *                                  is under the legal age on the order date
     */
    public void addItem(MenuItem item, int quantity) {
        OrderLine requested = new OrderLine(item, quantity);
        if (item.isAgeRestricted() && !customer.isAdultOn(orderDate)) {
            throw new AgeRestrictionException(
                    "Customer " + customer.getId() + " is under " + Customer.LEGAL_AGE
                            + " and cannot order " + item.getName());
        }
        OrderLine existing = lines.get(item.getId());
        OrderLine merged = existing == null
                ? requested
                : existing.withQuantity(existing.getQuantity() + quantity);
        lines.put(item.getId(), merged);
    }

    /**
     * Removes the line of the given item completely.
     *
     * @throws ItemNotInOrderException if the item is not in the order
     */
    public void removeItem(String itemId) {
        requireLine(itemId);
        lines.remove(itemId);
    }

    /**
     * Removes the given quantity of an item. When the whole quantity is removed,
     * the line is removed from the order.
     *
     * @throws ItemNotInOrderException  if the item is not in the order
     * @throws InvalidQuantityException if the quantity is not positive or more than ordered
     */
    public void removeItem(String itemId, int quantity) {
        OrderLine line = requireLine(itemId);
        if (quantity <= 0 || quantity > line.getQuantity()) {
            throw new InvalidQuantityException("Cannot remove " + quantity + " of " + itemId
                    + ", the order has " + line.getQuantity());
        }
        if (quantity == line.getQuantity()) {
            lines.remove(itemId);
        } else {
            lines.put(itemId, line.withQuantity(line.getQuantity() - quantity));
        }
    }

    /**
     * Returns the total price of the order, rounded to 2 decimals.
     */
    public BigDecimal getTotal() {
        return lines.values().stream()
                .map(OrderLine::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Returns the quantity of the given item in the order, or zero if it is not ordered.
     */
    public int getQuantityOf(String itemId) {
        OrderLine line = lines.get(itemId);
        return line == null ? 0 : line.getQuantity();
    }

    /**
     * Returns the lines of the order as a read-only list.
     */
    public List<OrderLine> getLines() {
        return List.copyOf(lines.values());
    }

    public boolean isEmpty() {
        return lines.isEmpty();
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    private OrderLine requireLine(String itemId) {
        OrderLine line = lines.get(itemId);
        if (line == null) {
            throw new ItemNotInOrderException("Item " + itemId + " is not in order " + id);
        }
        return line;
    }
}
