package lab3.service;

import java.util.List;

import lab3.exception.NotFoundException;
import lab3.exception.ValidationException;
import lab3.model.Car;
import lab3.repository.CarRepository;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car create(Car car) {
        validateCar(car);
        return carRepository.create(car);
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public List<Car> getAvailable() {
        return carRepository.findByStatus(Car.Status.Available);
    }

    public Car getById(int id) {
        return carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car"));
    }

    public Car update(Car car) {
        validateCar(car);
        return carRepository.update(car).orElseThrow(() -> new NotFoundException("Car"));
    }

    public void delete(int id) {
        carRepository.deleteById(id);
    }

    private static void validateCar(Car car) throws ValidationException {
        if (car.getMake().isBlank()) {
            throw new ValidationException("make", "cannot be blank");
        }
        if (car.getModel().isBlank()) {
            throw new ValidationException("model", "cannot be blank");
        }
        if (car.getVin() == 0) {
            throw new ValidationException("vin", "cannot be blank");
        }
        if (car.getCarClass() == null) {
            throw new ValidationException("carClass", "cannot be blank");
        }
        if (car.getCarStatus() == null) {
            throw new ValidationException("status", "cannot be blank");
        }
    }
}
