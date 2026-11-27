package com.restaurant.menu;

import com.restaurant.exception.DuplicateMenuItemException;
import com.restaurant.exception.InvalidMenuItemException;
import com.restaurant.exception.MenuItemNotFoundException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The menu of the restaurant. Items keep the order in which they were added.
 * <p>
 * The menu only stores and finds items (Single Responsibility); creating items
 * is the job of {@link MenuItemFactory}.
 */
public class Menu {

    private final Map<String, MenuItem> items = new LinkedHashMap<>();

    /**
     * Adds an item to the menu.
     *
     * @throws InvalidMenuItemException   if the item is null
     * @throws DuplicateMenuItemException if an item with the same id is already on the menu
     */
    public void addItem(MenuItem item) {
        if (item == null) {
            throw new InvalidMenuItemException("Cannot add a null item to the menu");
        }
        if (items.containsKey(item.getId())) {
            throw new DuplicateMenuItemException("Menu already contains an item with id " + item.getId());
        }
        items.put(item.getId(), item);
    }

    /**
     * Removes the item with the given id from the menu and returns it.
     *
     * @throws MenuItemNotFoundException if there is no item with that id
     */
    public MenuItem removeItem(String id) {
        MenuItem removed = items.remove(id);
        if (removed == null) {
            throw notFound(id);
        }
        return removed;
    }

    /**
     * Returns the item with the given id.
     *
     * @throws MenuItemNotFoundException if there is no item with that id
     */
    public MenuItem findById(String id) {
        MenuItem item = items.get(id);
        if (item == null) {
            throw notFound(id);
        }
        return item;
    }

    /**
     * Returns whether an item with the given id is on the menu.
     */
    public boolean contains(String id) {
        return items.containsKey(id);
    }

    /**
     * Returns all items on the menu as a read-only list.
     */
    public List<MenuItem> getItems() {
        return List.copyOf(items.values());
    }

    /**
     * Returns the items of one category as a read-only list.
     */
    public List<MenuItem> getItemsByCategory(MenuCategory category) {
        return items.values().stream()
                .filter(item -> item.getCategory() == category)
                .toList();
    }

    private static MenuItemNotFoundException notFound(String id) {
        return new MenuItemNotFoundException("No menu item with id " + id);
    }
}
