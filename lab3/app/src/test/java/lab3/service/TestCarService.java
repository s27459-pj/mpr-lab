package lab3.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lab3.exception.NotFoundException;
import lab3.exception.ValidationException;
import lab3.model.Car;
import lab3.model.Customer;
import lab3.repository.CarRepository;
import lab3.repository.CustomerRepository;
import lab3.repository.RentalRepository;

class TestCarService {
    private CarRepository carRepository;
    private RentalRepository rentalRepository;

    private CustomerService customerService;
    private CarService carService;

    @BeforeEach
    void setup() {
        carRepository = new CarRepository();
        rentalRepository = new RentalRepository();

        customerService = new CustomerService(new CustomerRepository());
        carService = new CarService(carRepository, rentalRepository, customerService);
    }

    @Test
    void serviceInitializes() {
        assertNotNull(carService);
    }

    static Stream<Arguments> invalidCreateOrUpdateArguments() {
        return Stream.of(
                // Blank fields
                Arguments.of("", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available),
                Arguments.of("Ford", "    ", 123456, Car.Class.Standard, Car.Status.Available),
                Arguments.of("Ford", "Fiesta", 0, Car.Class.Standard, Car.Status.Available),
                Arguments.of("Ford", "Fiesta", 123456, null, Car.Status.Available),
                Arguments.of("Ford", "Fiesta", 123456, Car.Class.Standard, null));
    }

    @ParameterizedTest
    @MethodSource("invalidCreateOrUpdateArguments")
    void createValidatesData(String make, String model, int vin, Car.Class carClass, Car.Status carStatus) {
        var car = new Car(make, model, vin, carClass, carStatus);
        assertThrows(ValidationException.class, () -> carService.create(car));
    }

    @Test
    void create() {
        var car = new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available);
        var createdCar = carService.create(car);

        assertNotNull(createdCar);
        assertNotNull(createdCar.getId());
        assertEquals(car.getMake(), createdCar.getMake());
        assertEquals(car.getModel(), createdCar.getModel());
        assertEquals(car.getVin(), createdCar.getVin());
        assertEquals(car.getCarClass(), createdCar.getCarClass());
        assertEquals(car.getCarStatus(), createdCar.getCarStatus());
    }

    @Test
    void getAll() {
        carRepository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        assertEquals(carRepository.count(), carService.getAll().size());
    }

    @Test
    void getAvailable() {
        carRepository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        carRepository.create(new Car("Ford", "Mondeo", 231231, Car.Class.Premium, Car.Status.Rented));
        assertEquals(1, carService.getAvailable().size());
    }

    @Test
    void getByIdNonExistent() {
        assertThrows(NotFoundException.class, () -> carService.getById(123));
    }

    @Test
    void getById() {
        var car = carRepository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        var foundCar = carService.getById(car.getId());
        assertEquals(car.getId(), foundCar.getId());
    }

    @ParameterizedTest
    @MethodSource("invalidCreateOrUpdateArguments")
    void updateValidatesData(String make, String model, int vin, Car.Class carClass, Car.Status carStatus) {
        var existingCar = carRepository
                .create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        var car = new Car(make, model, vin, carClass, carStatus);
        car.setId(existingCar.getId());
        assertThrows(ValidationException.class, () -> carService.update(car));
    }

    @Test
    void update() {
        var existingCar = carRepository
                .create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        var car = new Car("Ford", "Mondeo", 231231, Car.Class.Premium, Car.Status.Rented);
        car.setId(existingCar.getId());
        var updatedCar = carService.update(car);
        assertEquals(car.getMake(), updatedCar.getMake());
        assertEquals(car.getModel(), updatedCar.getModel());
        assertEquals(car.getVin(), updatedCar.getVin());
        assertEquals(car.getCarClass(), updatedCar.getCarClass());
        assertEquals(car.getCarStatus(), updatedCar.getCarStatus());
    }

    @Test
    void delete() {
        var car = carRepository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        carService.delete(car.getId());
        assertEquals(0, carRepository.count());
    }

    @Test
    void rentNonExistentCar() {
        var customer = customerService.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        assertThrows(NotFoundException.class, () -> carService.rent(123, customer.getId(), 14));
    }

    @Test
    void rentNonExistentCustomer() {
        var car = carRepository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        assertThrows(NotFoundException.class, () -> carService.rent(car.getId(), 123, 14));
    }

    @Test
    void rentUnavailable() {
        var car = carRepository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Rented));
        var customer = customerService.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        assertThrows(ValidationException.class, () -> carService.rent(car.getId(), customer.getId(), 14));
    }

    @Test
    void rentInvalidDays() {
        var car = carRepository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        var customer = customerService.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        assertThrows(ValidationException.class, () -> carService.rent(car.getId(), customer.getId(), 0));
    }

    @Test
    void rent() {
        var car = carRepository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        var customer = customerService.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        var rentedCar = carService.rent(car.getId(), customer.getId(), 14);

        assertEquals(car.getId(), rentedCar.getId());
        assertEquals(Car.Status.Rented, rentedCar.getCarStatus());
        assertEquals(1, rentalRepository.count());
        var rental = rentalRepository.findAll().get(0);
        assertEquals(car.getId(), rental.getCarId());
        assertEquals(customer.getId(), rental.getCustomerId());
        assertEquals(14, rental.getDays());
    }
}
