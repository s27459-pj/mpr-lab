package pw.karczewski.pizza.exceptions;

import pw.karczewski.pizza.model.Pizza;

public class PizzaUnavailableException extends Exception {
    public PizzaUnavailableException(Pizza pizza) {
        super(String.format("Pizza %s is not available", pizza.getName()));
    }

}
