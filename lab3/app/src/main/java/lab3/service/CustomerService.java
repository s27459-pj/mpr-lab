package lab3.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lab3.exception.NotFoundException;
import lab3.exception.ValidationException;
import lab3.model.Customer;
import lab3.repository.CustomerRepository;

public class CustomerService {
    private static final Logger log = LogManager.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer) {
        validateCustomer(customer);
        log.info("Created: {}", customer);
        return customerRepository.create(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(int id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer"));
    }

    public Customer update(Customer customer) {
        validateCustomer(customer);
        log.info("Updated: {}", customer);
        return customerRepository.update(customer).orElseThrow(() -> new NotFoundException("Customer"));
    }

    public void delete(int id) {
        log.info("Deleted: {}", id);
        customerRepository.deleteById(id);
    }

    private static void validateCustomer(Customer customer) throws ValidationException {
        if (customer.getFirstName().isBlank()) {
            throw new ValidationException("firstName", "cannot be blank");
        }
        if (customer.getLastName().isBlank()) {
            throw new ValidationException("lastName", "cannot be blank");
        }
        if (customer.getEmail().isBlank()) {
            throw new ValidationException("email", "cannot be blank");
        }
        if (!customer.getEmail().contains("@")) {
            throw new ValidationException("email", "must be a valid email address");
        }
        if (customer.getPhone().isBlank()) {
            throw new ValidationException("phone", "cannot be blank");
        }
    }
}
