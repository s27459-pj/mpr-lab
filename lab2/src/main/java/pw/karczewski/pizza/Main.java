package pw.karczewski.pizza;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import pw.karczewski.pizza.model.Pizza;
import pw.karczewski.pizza.service.PizzaService;

public class Main {
    public static void main(String[] args) {
        var pizzaService = getPizzaService();

        System.out.println("Welcome to the pizza service.");
        System.out.println("Available pizzas:");
        var availablePizzas = pizzaService.getAvailablePizzas();
        for (var pizza : availablePizzas) {
            System.out.println(String.format("[%d] %s", availablePizzas.indexOf(pizza), pizza));
        }

        try (var scanner = new Scanner(System.in)) {
            while (true) {
                makeOrder(pizzaService, scanner);
                System.out.print("Do you want to make another order? [y/n]: ");
                if (!scanner.nextLine().toLowerCase().startsWith("y")) {
                    break;
                }
            }
        }
    }

    private static PizzaService getPizzaService() {
        final var availablePizzas = List.of(
                new Pizza("Margherita", new BigDecimal("20.00")),
                new Pizza("Pepperoni", new BigDecimal("22.00"), false),
                new Pizza("Hawaiian", new BigDecimal("24.00")),
                new Pizza("Mushroom", new BigDecimal("28.00")),
                new Pizza("Capricciosa", new BigDecimal("26.00")));

        return new PizzaService(availablePizzas);
    }

    private static void makeOrder(PizzaService pizzaService, Scanner scanner) {
        List<Pizza> selectedPizzas;

        System.out.print("Select some pizzas (separated by comma): ");
        final var input = scanner.nextLine();
        try {
            selectedPizzas = List.of(input.split(",")).stream()
                    .map(e -> Integer.parseInt(e.strip()))
                    .map(pizzaService.getAvailablePizzas()::get)
                    .toList();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid pizza number");
            return;
        }

        try {
            var order = pizzaService.makeOrder(selectedPizzas);
            System.out.println(order.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
