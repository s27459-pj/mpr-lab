package lab3.repository;

import java.util.Optional;

import lab3.model.Rental;

public class RentalRepository extends Repository<Rental> {
    public Optional<Rental> update(Rental rental) {
        var rentalToUpdate = findById(rental.getId());
        if (rentalToUpdate.isEmpty())
            return Optional.empty();
        return rentalToUpdate.map(r -> {
            r.setCarId(rental.getCarId());
            r.setCustomerId(rental.getCustomerId());
            r.setDays(rental.getDays());
            r.setPrice(rental.getPrice());
            return r;
        });
    }
}
