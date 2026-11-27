package com.restaurant.menu;

import com.restaurant.exception.DuplicateMenuItemException;
import com.restaurant.exception.InvalidMenuItemException;
import com.restaurant.exception.MenuItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuTest {

    private Menu menu;
    private MenuItem burger;
    private MenuItem cola;
    private MenuItem baklava;

    @BeforeEach
    void setUp() {
        menu = new Menu();
        burger = new Food("F-1", "Burger", new BigDecimal("180.00"), 15, false);
        cola = new Drink("D-1", "Cola", new BigDecimal("40.00"), 330, false);
        baklava = new Dessert("S-1", "Baklava", new BigDecimal("90.00"), false);
    }

    @Test
    void newMenuIsEmpty() {
        assertTrue(menu.getItems().isEmpty());
    }

    @Test
    void addedItemCanBeFoundById() {
        menu.addItem(burger);
        assertSame(burger, menu.findById("F-1"));
    }

    @Test
    void getItemsReturnsItemsInInsertionOrder() {
        menu.addItem(cola);
        menu.addItem(burger);
        assertEquals(List.of(cola, burger), menu.getItems());
    }

    @Test
    void getItemsReturnsReadOnlyList() {
        menu.addItem(burger);
        assertThrows(UnsupportedOperationException.class, () -> menu.getItems().add(cola));
    }

    @Test
    void addingDuplicateIdThrowsDuplicateMenuItemException() {
        menu.addItem(burger);
        MenuItem sameId = new Food("F-1", "Pizza", new BigDecimal("200.00"), 20, true);
        assertThrows(DuplicateMenuItemException.class, () -> menu.addItem(sameId));
    }

    @Test
    void addingNullItemThrowsInvalidMenuItemException() {
        assertThrows(InvalidMenuItemException.class, () -> menu.addItem(null));
    }

    @Test
    void findByUnknownIdThrowsMenuItemNotFoundException() {
        assertThrows(MenuItemNotFoundException.class, () -> menu.findById("X-99"));
    }

    @Test
    void notFoundMessageContainsTheId() {
        MenuItemNotFoundException ex = assertThrows(MenuItemNotFoundException.class, () -> menu.findById("X-99"));
        assertTrue(ex.getMessage().contains("X-99"));
    }

    @Test
    void removeItemDeletesItFromMenu() {
        menu.addItem(burger);
        menu.removeItem("F-1");
        assertFalse(menu.contains("F-1"));
    }

    @Test
    void removeItemReturnsRemovedItem() {
        menu.addItem(burger);
        assertSame(burger, menu.removeItem("F-1"));
    }

    @Test
    void removeUnknownItemThrowsMenuItemNotFoundException() {
        assertThrows(MenuItemNotFoundException.class, () -> menu.removeItem("X-99"));
    }

    @Test
    void containsReturnsTrueForAddedItem() {
        menu.addItem(cola);
        assertTrue(menu.contains("D-1"));
    }

    @Test
    void getItemsByCategoryReturnsOnlyThatCategory() {
        menu.addItem(burger);
        menu.addItem(cola);
        menu.addItem(baklava);
        assertEquals(List.of(cola), menu.getItemsByCategory(MenuCategory.DRINK));
    }
}
