package pw.karczewski.pizza.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class Order {
    // Running counter of all orders
    static private int totalOrders = 1;

    private int orderNumber;
    private List<Pizza> pizzas;
    private BigDecimal price;

    public Order(List<Pizza> pizzas, BigDecimal price) {
        this.orderNumber = Order.totalOrders++;
        this.pizzas = pizzas;
        this.price = price;
    }

    public String toString() {
        return String.format("Order #%d: %s pizzas, total price: %s", this.orderNumber, this.pizzas.size(), this.price);
    }
}
