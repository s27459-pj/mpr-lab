package pw.karczewski.pizza.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {
    // Running counter of all orders
    static private int totalOrders = 1;

    final private int orderNumber;
    final private List<Pizza> pizzas;
    final private BigDecimal price;

    public Order(List<Pizza> pizzas, BigDecimal price) {
        this.orderNumber = Order.totalOrders++;
        this.pizzas = pizzas;
        this.price = price;
    }
}
