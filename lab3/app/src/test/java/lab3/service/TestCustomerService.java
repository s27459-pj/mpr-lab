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
import lab3.model.Customer;
import lab3.repository.CustomerRepository;

class TestCustomerService {
    private CustomerRepository repository;
    private CustomerService service;

    @BeforeEach
    void setup() {
        repository = new CustomerRepository();
        service = new CustomerService(repository);
    }

    @Test
    void serviceInitializes() {
        assertNotNull(service);
    }

    static Stream<Arguments> createOrUpdateArguments() {
        return Stream.of(
                // Blank fields
                Arguments.of(" ", "Doe", "john@doe.com", "123-123-123"),
                Arguments.of("John", "", "john@doe.com", "123-123-123"),
                Arguments.of("John", "Doe", " ", "123-123-123"),
                Arguments.of("John", "Doe", "john@doe.com", ""),
                // Invalid email address
                Arguments.of("John", "Doe", "doe.com", "123-123-123"));
    }

    @ParameterizedTest
    @MethodSource("createOrUpdateArguments")
    void createValidatesData(String firstName, String lastName, String email, String phone) {
        var customer = new Customer(firstName, lastName, email, phone);
        assertThrows(ValidationException.class, () -> service.create(customer));
    }

    @Test
    void create() {
        var customer = new Customer("John", "Doe", "john@doe.com", "123-123-123");
        var createdCustomer = service.create(customer);

        assertNotNull(createdCustomer);
        assertNotNull(createdCustomer.getId());
        assertEquals(customer.getFirstName(), createdCustomer.getFirstName());
        assertEquals(customer.getLastName(), createdCustomer.getLastName());
        assertEquals(customer.getEmail(), createdCustomer.getEmail());
        assertEquals(customer.getPhone(), createdCustomer.getPhone());
    }

    @Test
    void getAll() {
        repository.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        assertEquals(service.getAll().size(), repository.count());
    }

    @Test
    void getByIdNonExistent() {
        assertThrows(NotFoundException.class, () -> service.getById(123));
    }

    @Test
    void getById() {
        var customer = new Customer("John", "Doe", "john@doe.com", "123-123-123");
        repository.create(customer);
        var foundCustomer = service.getById(customer.getId());
        assertEquals(customer.getId(), foundCustomer.getId());
    }

    @ParameterizedTest
    @MethodSource("createOrUpdateArguments")
    void updateValidatesData(String firstName, String lastName, String email, String phone) {
        var existingCustomer = repository.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        var customer = new Customer(firstName, lastName, email, phone);
        customer.setId(existingCustomer.getId());
        assertThrows(ValidationException.class, () -> service.update(customer));
    }

    @Test
    void update() {
        var existingCustomer = repository.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        var customer = new Customer("Jane", "Doe", "jane@doe.com", "321-321-321");
        customer.setId(existingCustomer.getId());
        var updatedCustomer = service.update(customer);
        assertEquals(customer.getFirstName(), updatedCustomer.getFirstName());
        assertEquals(customer.getLastName(), updatedCustomer.getLastName());
        assertEquals(customer.getEmail(), updatedCustomer.getEmail());
        assertEquals(customer.getPhone(), updatedCustomer.getPhone());
    }

    @Test
    void delete() {
        var customer = repository.create(new Customer("John", "Doe", "john@doe.com", "123-123-123"));
        service.delete(customer.getId());
        assertEquals(0, repository.count());
    }
}
