package lab3.repository;

import java.util.List;
import java.util.Optional;

import lab3.model.Car;

public class CarRepository extends Repository<Car> {
    public List<Car> findByStatus(Car.Status status) {
        return objects.stream().filter(car -> car.getCarStatus().equals(status)).toList();
    }

    public Optional<Car> update(Car car) {
        var carToUpdate = findById(car.getId());
        if (carToUpdate.isEmpty())
            return Optional.empty();
        return carToUpdate.map(c -> {
            c.setMake(car.getMake());
            c.setModel(car.getModel());
            c.setVin(car.getVin());
            c.setCarClass(car.getCarClass());
            c.setCarStatus(car.getCarStatus());
            return c;
        });
    }
}
