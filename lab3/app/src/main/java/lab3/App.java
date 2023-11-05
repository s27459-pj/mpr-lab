package lab3;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lab3.model.Car;
import lab3.model.Customer;
import lab3.repository.CarRepository;
import lab3.repository.CustomerRepository;
import lab3.repository.RentalRepository;
import lab3.service.CarService;
import lab3.service.CustomerService;

public class App {
    private static final Logger log = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        var customerService = new CustomerService(new CustomerRepository());
        var carService = new CarService(new CarRepository(), new RentalRepository(), customerService);

        var customer = customerService.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        carService.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        carService.create(new Car("Ford", "Mondeo", 321321, Car.Class.Premium, Car.Status.Repair));
        carService.create(new Car("Ford", "Mondeo", 123321, Car.Class.Premium, Car.Status.Rented));
        carService.create(new Car("Pontiac", "Aztek", 322131, Car.Class.Standard, Car.Status.Available));
        carService.create(new Car("Toyota", "Corolla", 654321, Car.Class.Standard, Car.Status.Available));
        carService.create(new Car("Tesla", "Model S", 987654, Car.Class.Luxury, Car.Status.Available));

        System.out.printf("Hello %s %s, which car would You like to rent?\n",
                customer.getFirstName(), customer.getLastName());
        for (var car : carService.getAvailable()) {
            System.out.println(String.format("[%d]\t%s %s", car.getId(), car.getMake(), car.getModel()));
        }

        System.out.print("Enter car ID: ");
        try (var scanner = new Scanner(System.in)) {
            var carId = scanner.nextInt();
            System.out.printf("For how many days would You like to rent the %s %s? ",
                    carService.getById(carId).getMake(), carService.getById(carId).getModel());
            var days = scanner.nextInt();

            var rentedCar = carService.rent(carId, customer.getId(), days);
            System.out.println(String.format("You have rented %s %s for %d days!",
                    rentedCar.getMake(), rentedCar.getModel(), days));
        } catch (Exception e) {
            log.error(e);
        }
    }
}
