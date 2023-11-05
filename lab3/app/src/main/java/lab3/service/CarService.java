package lab3.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lab3.exception.NotFoundException;
import lab3.exception.ValidationException;
import lab3.model.Car;
import lab3.model.Rental;
import lab3.repository.CarRepository;
import lab3.repository.RentalRepository;

public class CarService {
    private static final Logger log = LogManager.getLogger(CarService.class);

    private final CarRepository carRepository;
    private final RentalRepository rentalRepository;

    private final CustomerService customerService;

    public CarService(CarRepository carRepository,
            RentalRepository rentalRepository,
            CustomerService customerService) {
        this.carRepository = carRepository;
        this.rentalRepository = rentalRepository;
        this.customerService = customerService;
    }

    public Car create(Car car) {
        validateCar(car);
        log.info("Created: {}", car);
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
        log.info("Updated: {}", car);
        return carRepository.update(car).orElseThrow(() -> new NotFoundException("Car"));
    }

    public void delete(int id) {
        log.info("Deleted: {}", id);
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

    public Car rent(int carId, int customerId, int days) {
        if (days <= 0) {
            throw new ValidationException("days", "must be greater than 0");
        }

        var car = getById(carId);
        if (car.getCarStatus() != Car.Status.Available) {
            throw new ValidationException("status", "must be available");
        }
        var customer = customerService.getById(customerId);

        // TODO: Calculate price
        rentalRepository.create(new Rental(car.getId(), customer.getId(), days, 1000));
        car.setCarStatus(Car.Status.Rented);
        log.info("Rented: {}", car);
        return update(car);
    }
}
