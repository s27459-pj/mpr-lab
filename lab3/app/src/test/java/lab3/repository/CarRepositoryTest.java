package lab3.repository;

import org.junit.jupiter.api.Test;

import lab3.model.Car;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class CarRepositoryTest {
    private CarRepository repository;

    @BeforeEach
    void setup() {
        repository = new CarRepository();
    }

    @Test
    void findByStatus() {
        repository.create(new Car("make1", "model1", 123, Car.Class.Standard, Car.Status.Available));
        repository.create(new Car("make2", "model2", 123, Car.Class.Standard, Car.Status.Available));
        repository.create(new Car("make3", "model3", 123, Car.Class.Standard, Car.Status.Rented));

        assertEquals(2, repository.findByStatus(Car.Status.Available).size());
        assertEquals(1, repository.findByStatus(Car.Status.Rented).size());
    }

    @Test
    void update() {
        var car = repository.create(new Car("make1", "model2", 123, Car.Class.Standard, Car.Status.Available));
        var updatedCar = new Car("make2", "model2", 321, Car.Class.Luxury, Car.Status.Rented);
        updatedCar.setId(car.getId());
        var result = repository.update(updatedCar);
        assertTrue(result.isPresent());
        assertEquals(updatedCar.getMake(), result.get().getMake());
        assertEquals(updatedCar.getModel(), result.get().getModel());
        assertEquals(updatedCar.getVin(), result.get().getVin());
        assertEquals(updatedCar.getCarClass(), result.get().getCarClass());
        assertEquals(updatedCar.getCarStatus(), result.get().getCarStatus());
    }
}
