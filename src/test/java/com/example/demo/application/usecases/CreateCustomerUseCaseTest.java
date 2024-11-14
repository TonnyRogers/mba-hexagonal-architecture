package com.example.demo.application.usecases;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.models.Customer;
import com.example.demo.services.CustomerService;

public class CreateCustomerUseCaseTest {

    @Test
    @DisplayName("should create a customer")
    public void testCreateCustomer() {
        // given
        final var CPF = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);
        final var customerService = Mockito.mock(CustomerService.class);

        // when
        Mockito.when(customerService.findByCpf(CPF)).thenReturn(Optional.empty());
        Mockito.when(customerService.findByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(customerService.save(Mockito.any())).thenAnswer(a -> {
            var customer = a.getArgument(0, Customer.class);
            customer.setId(UUID.randomUUID().getMostSignificantBits());
            return customer;
        });

        final var useCase = new CreateCustomerUseCase(customerService);
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
        final var CPF = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Customer already exists";

        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);
        final var customerService = Mockito.mock(CustomerService.class);

        final var createdCustomer = new Customer();
        createdCustomer.setCpf(CPF);
        createdCustomer.setName(name);
        createdCustomer.setEmail(email);
        createdCustomer.setId(UUID.randomUUID().getMostSignificantBits());

        // when
        Mockito.when(customerService.findByCpf(CPF)).thenReturn(Optional.of(createdCustomer));

        final var useCase = new CreateCustomerUseCase(customerService);
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }

    @Test
    @DisplayName("should't create a customer with duplicated email")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var CPF = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Customer already exists";

        final var createInput = new CreateCustomerUseCase.Input(CPF, email, name);
        final var customerService = Mockito.mock(CustomerService.class);

        final var createdCustomer = new Customer();
        createdCustomer.setCpf(CPF);
        createdCustomer.setName(name);
        createdCustomer.setEmail(email);
        createdCustomer.setId(UUID.randomUUID().getMostSignificantBits());

        // when
        Mockito.when(customerService.findByEmail(email)).thenReturn(Optional.of(createdCustomer));

        final var useCase = new CreateCustomerUseCase(customerService);
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }
}
