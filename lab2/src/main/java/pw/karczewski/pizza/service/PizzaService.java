package pw.karczewski.pizza.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pw.karczewski.pizza.exceptions.PizzaUnavailableException;
import pw.karczewski.pizza.model.Order;
import pw.karczewski.pizza.model.Pizza;

public class PizzaService {
    private static final Logger logger = LogManager.getLogger(PizzaService.class);
    private List<Pizza> pizzas;
    private List<Order> orders;

    public PizzaService(List<Pizza> availablePizzas) {
        this.pizzas = availablePizzas;
        this.orders = new ArrayList<Order>();
    }

    public Order makeOrder(List<Pizza> pizzas) throws PizzaUnavailableException {
        logger.info("Making order with {} pizza(s)", pizzas.size());
        for (var pizza : pizzas) {
            if (!pizza.isAvailable()) {
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
}
