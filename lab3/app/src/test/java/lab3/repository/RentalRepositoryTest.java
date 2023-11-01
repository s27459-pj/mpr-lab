package lab3.repository;

import org.junit.jupiter.api.Test;

import lab3.model.Rental;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class RentalRepositoryTest {
    private RentalRepository repository;

    @BeforeEach
    void setup() {
        repository = new RentalRepository();
    }

    @Test
    void update() {
        var rental = repository.create(new Rental(101, 201, 10, 1000_00));
        var updatedRental = new Rental(102, 202, 20, 2000_00);
        updatedRental.setId(rental.getId());
        var result = repository.update(updatedRental);
        assertTrue(result.isPresent());
        assertEquals(updatedRental.getCarId(), result.get().getCarId());
        assertEquals(updatedRental.getCustomerId(), result.get().getCustomerId());
        assertEquals(updatedRental.getDays(), result.get().getDays());
        assertEquals(updatedRental.getPrice(), result.get().getPrice());
    }
}
