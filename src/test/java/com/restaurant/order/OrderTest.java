package com.restaurant.order;

import com.restaurant.customer.Customer;
import com.restaurant.exception.AgeRestrictionException;
import com.restaurant.exception.InvalidCustomerException;
import com.restaurant.exception.InvalidMenuItemException;
import com.restaurant.exception.InvalidOrderException;
import com.restaurant.exception.InvalidQuantityException;
import com.restaurant.exception.ItemNotInOrderException;
import com.restaurant.menu.Dessert;
import com.restaurant.menu.Drink;
import com.restaurant.menu.Food;
import com.restaurant.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest {

    private static final LocalDate ORDER_DATE = LocalDate.of(2026, 11, 28);

    private final Customer adult = new Customer("C-1", "Ayse", LocalDate.of(1995, 3, 10));
    private final Customer minor = new Customer("C-2", "Can", LocalDate.of(2012, 5, 1));

    private final MenuItem burger = new Food("F-1", "Burger", new BigDecimal("180.00"), 15, false);
    private final MenuItem cola = new Drink("D-1", "Cola", new BigDecimal("40.00"), 330, false);
    private final MenuItem beer = new Drink("D-2", "Beer", new BigDecimal("100.00"), 500, true);
    private final MenuItem baklava = new Dessert("S-1", "Baklava", new BigDecimal("90.00"), true);

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order("O-1", adult, ORDER_DATE);
    }

    @Test
    void newOrderIsEmpty() {
        assertTrue(order.isEmpty());
    }

    @Test
    void newOrderTotalIsZero() {
        assertEquals(new BigDecimal("0.00"), order.getTotal());
    }

    @Test
    void blankIdThrowsInvalidOrderException() {
        assertThrows(InvalidOrderException.class, () -> new Order(" ", adult, ORDER_DATE));
    }

    @Test
    void nullDateThrowsInvalidOrderException() {
        assertThrows(InvalidOrderException.class, () -> new Order("O-2", adult, null));
    }

    @Test
    void nullCustomerThrowsInvalidCustomerException() {
        assertThrows(InvalidCustomerException.class, () -> new Order("O-2", null, ORDER_DATE));
    }

    @Test
    void addItemCreatesLineWithQuantity() {
        order.addItem(burger, 2);
        assertEquals(2, order.getQuantityOf("F-1"));
    }

    @Test
    void addingSameItemTwiceIncreasesQuantity() {
        order.addItem(burger, 2);
        order.addItem(burger, 1);
        assertEquals(1, order.getLines().size());
        assertEquals(3, order.getQuantityOf("F-1"));
    }

    @Test
    void addItemWithZeroQuantityThrowsInvalidQuantityException() {
        assertThrows(InvalidQuantityException.class, () -> order.addItem(burger, 0));
    }

    @Test
    void addNullItemThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> order.addItem(null, 1));
    }

    @Test
    void totalIsSumOfLineTotals() {
        order.addItem(burger, 2);   // 360.00
        order.addItem(cola, 3);     // 120.00
        order.addItem(baklava, 1);  // 105.00 (with ice cream)
        assertEquals(new BigDecimal("585.00"), order.getTotal());
    }

    @Test
    void totalIncludesAlcoholTax() {
        order.addItem(beer, 2);     // 2 x 120.00
        assertEquals(new BigDecimal("240.00"), order.getTotal());
    }

    @Test
    void adultCanOrderAlcoholicDrink() {
        order.addItem(beer, 1);
        assertEquals(1, order.getQuantityOf("D-2"));
    }

    @Test
    void minorOrderingAlcoholicDrinkThrowsAgeRestrictionException() {
        Order minorOrder = new Order("O-2", minor, ORDER_DATE);
        assertThrows(AgeRestrictionException.class, () -> minorOrder.addItem(beer, 1));
    }

    @Test
    void minorCanOrderNonAlcoholicDrink() {
        Order minorOrder = new Order("O-2", minor, ORDER_DATE);
        minorOrder.addItem(cola, 1);
        assertEquals(1, minorOrder.getQuantityOf("D-1"));
    }

    @Test
    void ageIsCheckedOnOrderDateNotToday() {
        Order futureOrder = new Order("O-3", minor, LocalDate.of(2030, 5, 1));
        futureOrder.addItem(beer, 1);
        assertEquals(1, futureOrder.getQuantityOf("D-2"));
    }

    @Test
    void removeItemDeletesWholeLine() {
        order.addItem(burger, 3);
        order.removeItem("F-1");
        assertEquals(0, order.getQuantityOf("F-1"));
        assertTrue(order.isEmpty());
    }

    @Test
    void removeItemNotInOrderThrowsItemNotInOrderException() {
        assertThrows(ItemNotInOrderException.class, () -> order.removeItem("F-1"));
    }

    @Test
    void removePartOfQuantityKeepsRemainingQuantity() {
        order.addItem(burger, 3);
        order.removeItem("F-1", 2);
        assertEquals(1, order.getQuantityOf("F-1"));
    }

    @Test
    void removeWholeQuantityRemovesLine() {
        order.addItem(burger, 3);
        order.removeItem("F-1", 3);
        assertFalse(order.getLines().stream().anyMatch(l -> l.getItem().equals(burger)));
    }

    @Test
    void removeMoreThanOrderedThrowsInvalidQuantityException() {
        order.addItem(burger, 1);
        assertThrows(InvalidQuantityException.class, () -> order.removeItem("F-1", 2));
    }

    @Test
    void removeZeroQuantityThrowsInvalidQuantityException() {
        order.addItem(burger, 1);
        assertThrows(InvalidQuantityException.class, () -> order.removeItem("F-1", 0));
    }

    @Test
    void removeQuantityOfItemNotInOrderThrowsItemNotInOrderException() {
        assertThrows(ItemNotInOrderException.class, () -> order.removeItem("F-1", 1));
    }

    @Test
    void totalIsUpdatedAfterRemovingItem() {
        order.addItem(burger, 1);
        order.addItem(cola, 1);
        order.removeItem("D-1");
        assertEquals(new BigDecimal("180.00"), order.getTotal());
    }

    @Test
    void getLinesReturnsReadOnlyList() {
        order.addItem(burger, 1);
        assertThrows(UnsupportedOperationException.class, () -> order.getLines().clear());
    }
}
