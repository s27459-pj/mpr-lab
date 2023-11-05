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
import lab3.repository.CarRepository;

class TestCarService {
    private CarRepository repository;
    private CarService service;

    @BeforeEach
    void setup() {
        repository = new CarRepository();
        service = new CarService(repository);
    }

    @Test
    void serviceInitializes() {
        assertNotNull(service);
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
        assertThrows(ValidationException.class, () -> service.create(car));
    }

    @Test
    void create() {
        var car = new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available);
        var createdCar = service.create(car);

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
        repository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        assertEquals(repository.count(), service.getAll().size());
    }

    @Test
    void getAvailable() {
        repository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        repository.create(new Car("Ford", "Mondeo", 231231, Car.Class.Premium, Car.Status.Rented));
        assertEquals(1, service.getAvailable().size());
    }

    @Test
    void getByIdNonExistent() {
        assertThrows(NotFoundException.class, () -> service.getById(123));
    }

    @Test
    void getById() {
        var car = repository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        var foundCar = service.getById(car.getId());
        assertEquals(car.getId(), foundCar.getId());
    }

    @ParameterizedTest
    @MethodSource("invalidCreateOrUpdateArguments")
    void updateValidatesData(String make, String model, int vin, Car.Class carClass, Car.Status carStatus) {
        var existingCar = repository
                .create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        var car = new Car(make, model, vin, carClass, carStatus);
        car.setId(existingCar.getId());
        assertThrows(ValidationException.class, () -> service.update(car));
    }

    @Test
    void update() {
        var existingCar = repository
                .create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        var car = new Car("Ford", "Mondeo", 231231, Car.Class.Premium, Car.Status.Rented);
        car.setId(existingCar.getId());
        var updatedCar = service.update(car);
        assertEquals(car.getMake(), updatedCar.getMake());
        assertEquals(car.getModel(), updatedCar.getModel());
        assertEquals(car.getVin(), updatedCar.getVin());
        assertEquals(car.getCarClass(), updatedCar.getCarClass());
        assertEquals(car.getCarStatus(), updatedCar.getCarStatus());
    }

    @Test
    void delete() {
        var car = repository.create(new Car("Ford", "Fiesta", 123456, Car.Class.Standard, Car.Status.Available));
        service.delete(car.getId());
        assertEquals(0, repository.count());
    }
}
