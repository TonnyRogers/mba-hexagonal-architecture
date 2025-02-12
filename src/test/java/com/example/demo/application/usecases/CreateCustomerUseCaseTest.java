package com.example.demo.application.usecases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.InMemoryCustomerRepository;
import com.example.demo.application.domain.Customer;
import com.example.demo.application.exceptions.ValidationException;

public class CreateCustomerUseCaseTest {

    @Test
    @DisplayName("should create a customer")
    public void testCreateCustomer() {
        // given
        final var CPF = "265.359.485-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);
        final var customerRepository = new InMemoryCustomerRepository();

        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var output = useCase.execute(createInput);

        //then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(CPF, output.cpf());
        Assertions.assertEquals(email, output.email());
        Assertions.assertEquals(name, output.name());
    }

    @Test
    @DisplayName("should't create a customer with duplicated CPF")
    public void testCreateWithDuplicatedCPFShouldFail() {
        // given
        final var CPF = "265.359.485-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Customer already exists";

        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);
        final var customerRepository = new InMemoryCustomerRepository();

        final var createdCustomer = Customer.newCustomer(name, CPF, email);
        customerRepository.create(createdCustomer);

        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }

    @Test
    @DisplayName("should't create a customer with duplicated email")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var CPF = "265.359.485-02";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Customer already exists";

        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);
        final var customerRepository = new InMemoryCustomerRepository();

        final var createdCustomer = Customer.newCustomer(name, CPF, email);
        customerRepository.create(createdCustomer);

        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }
}
