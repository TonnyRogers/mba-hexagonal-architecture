package com.example.demo.application.usecases.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.IntegrationTest;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.jpa.models.CustomerEntity;
import com.example.demo.infrastructure.jpa.repositories.CustomerJpaRepository;

public class CreateCustomerUseCaseIT extends IntegrationTest {

    @Autowired
    private CreateCustomerUseCase useCase;

    @Autowired
    private CustomerJpaRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("should create customer")
    public void testCreateCustomer() {
        // given
        final var CPF = "265.359.4855-51";
        final var email = "teste@teste123.com";
        final var name = "Tony Amaral";

        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);

        final var output = useCase.execute(createInput);
        createCustomer(CPF, email, name);

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
        final var CPF = "265.359.4855-51";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Customer already exists";

        createCustomer(CPF, email, name);
        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);

        // when
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }

    @Test
    @DisplayName("should't create a customer with duplicated email")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var CPF = "265.359.4855-51";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Customer already exists";

        createCustomer("485.466.4645-48", email, name);
        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);

        // when
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }

    private CustomerEntity createCustomer(final String cpf, final String email, final String name) {
        final var createdCustomer = new CustomerEntity();
        createdCustomer.setCpf(cpf);
        createdCustomer.setName(name);
        createdCustomer.setEmail(email);

        return customerRepository.save(createdCustomer);
    }
}
