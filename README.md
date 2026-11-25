# Restaurant Ordering – Advanced OOP Final Project

A small restaurant ordering domain written in **Java 17** with **JUnit 5**.
There is no `main` method, no UI and no console output: the unit tests are the only way the code runs.

## Run the tests

```bash
mvn test
```

## Domain

| Entity | Description |
|---|---|
| `MenuItem` (abstract) → `Food`, `Drink`, `Dessert` | Items on the menu, each type with its own rules |
| `Menu` | The restaurant menu (get the menu, find an item) |
| `Customer` | The customer who places an order |
| `Order` / `OrderLine` | An order and its lines (item + quantity) |
| `Payment`, `PaymentMethod` → `CashPayment`, `CreditCardPayment` | Payments with different rules |

**Operations:** get the menu, create an order, add / remove an item, get the order total, pay an order, cancel an order.

**Payment rules:**
- *Cash*: the given amount must cover the total; change is returned.
- *Credit card*: the card must not be expired on the payment date and the total must be within the card limit.

Dates are always passed as parameters – the system clock is never read.

## Design patterns (planned)

| Pattern | Where | Why |
|---|---|---|
| Strategy | `PaymentMethod` | Each payment method has its own validation rules |
| State | `OrderState` (`Open`, `Paid`, `Cancelled`) | Allowed operations depend on the order status |
| Factory | `MenuItemFactory` | Creates the right `MenuItem` subtype from a category |
| Observer | `OrderEventListener` | Notify interested parties when an order is paid / cancelled |
| Repository | `OrderRepository` | Hides storage behind an interface (DIP, testability) |

## Exception hierarchy

All exceptions are **unchecked** and extend `RestaurantException`.

```
RestaurantException
├── MenuItemNotFoundException
├── DuplicateMenuItemException
├── InvalidQuantityException
├── OrderNotFoundException
├── ItemNotInOrderException
├── InvalidOrderStateException
├── EmptyOrderException
└── PaymentException
    ├── InsufficientPaymentException
    ├── CardExpiredException
    └── CardLimitExceededException
```
