package lab3.repository;

import org.junit.jupiter.api.Test;

import lab3.model.Customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class CustomerRepositoryTest {
    private CustomerRepository repository;

    @BeforeEach
    void setup() {
        repository = new CustomerRepository();
    }

    @Test
    void update() {
        var customer = repository.create(new Customer("John", "Doe", "john@doe.com", "123-456-789"));
        var updatedCustomer = new Customer("Jane", "Though", "jane@though.com", "987-654-321");
        updatedCustomer.setId(customer.getId());
        var result = repository.update(updatedCustomer);
        assertTrue(result.isPresent());
        assertEquals(updatedCustomer.getFirstName(), result.get().getFirstName());
        assertEquals(updatedCustomer.getLastName(), result.get().getLastName());
        assertEquals(updatedCustomer.getEmail(), result.get().getEmail());
        assertEquals(updatedCustomer.getPhone(), result.get().getPhone());
    }
}
