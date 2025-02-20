package com.example.demo.application.usecases.customer;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.domain.customer.Customer;
import com.example.demo.application.repository.InMemoryCustomerRepository;

public class GetCustomerByIdUseCaseTest {

    @Test
    @DisplayName("should get customer by id")
    public void testGetById() {
        final var CPF = "265.359.485-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var customerRepository = new InMemoryCustomerRepository();

        final var createdCustomer = Customer.newCustomer(name, CPF, email);

        final var expectedId = createdCustomer.getCustomerId().value();

        final var input = new GetCustomerByIdUseCase.Input(expectedId);
        customerRepository.create(createdCustomer);

        final var useCase = new GetCustomerByIdUseCase(customerRepository);
        final var output = useCase.execute(input).get();
        // this get method above is only allowed to use in test scenario

        Assertions.assertEquals(expectedId, output.id());
        Assertions.assertEquals(CPF, output.cpf());
        Assertions.assertEquals(email, output.email());
        Assertions.assertEquals(name, output.name());
    }

    @Test
    @DisplayName("should't get customer by id")
    public void testGetByIdFail() {

        final var id = UUID.randomUUID().toString();

        final var customerRepository = new InMemoryCustomerRepository();
        final var input = new GetCustomerByIdUseCase.Input(id);

        final var useCase = new GetCustomerByIdUseCase(customerRepository);
        final var output = useCase.execute(input);

        Assertions.assertTrue(output.isEmpty());
    }
}
