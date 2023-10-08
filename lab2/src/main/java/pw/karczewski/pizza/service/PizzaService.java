package pw.karczewski.pizza.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pw.karczewski.pizza.exceptions.PizzaUnavailableException;
import pw.karczewski.pizza.model.Order;
import pw.karczewski.pizza.model.Pizza;

public class PizzaService {
    private List<Pizza> pizzas;
    private List<Order> orders;

    public PizzaService(List<Pizza> availablePizzas) {
        this.pizzas = availablePizzas;
        this.orders = new ArrayList<Order>();
    }

    public Order makeOrder(List<Pizza> pizzas) throws PizzaUnavailableException {
        for (var pizza : pizzas) {
            if (!this.pizzas.contains(pizza)) {
                throw new PizzaUnavailableException(pizza);
            }
        }
        var price = pizzas.stream().map(Pizza::getPrice).reduce(BigDecimal::add).get();
        var order = new Order(pizzas, price);
        this.orders.add(order);
        return order;
    }

    public List<Pizza> getPizzas() {
        return this.pizzas;
    }

    public List<Pizza> getAvailablePizzas() {
        return this.pizzas.stream().filter(Pizza::isAvailable).toList();
    }
}
