package lab3.repository;

import java.util.Optional;

import lab3.model.Customer;

public class CustomerRepository extends Repository<Customer> {
    public Optional<Customer> update(Customer customer) {
        var customerToUpdate = findById(customer.getId());
        if (customerToUpdate.isEmpty())
            return Optional.empty();
        return customerToUpdate.map(c -> {
            c.setFirstName(customer.getFirstName());
            c.setLastName(customer.getLastName());
            c.setEmail(customer.getEmail());
            c.setPhone(customer.getPhone());
            return c;
        });
    }
}
