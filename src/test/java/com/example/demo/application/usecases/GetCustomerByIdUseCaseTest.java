package com.example.demo.application.usecases;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.models.Customer;
import com.example.demo.services.CustomerService;

public class GetCustomerByIdUseCaseTest {

    @Test
    @DisplayName("should get customer by id")
    public void testGetById() {

        final var id = UUID.randomUUID().getMostSignificantBits();
        final var CPF = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var customerService = Mockito.mock(CustomerService.class);

        final var createdCustomer = new Customer();
        createdCustomer.setId(id);
        createdCustomer.setCpf(CPF);
        createdCustomer.setName(name);
        createdCustomer.setEmail(email);

        final var input = new GetCustomerByIdUseCase.Input(id);

        Mockito.when(customerService.findById(id)).thenReturn(Optional.of(createdCustomer));

        final var useCase = new GetCustomerByIdUseCase(customerService);
        final var output = useCase.execute(input).get();
        // this get method above is only allowed to use in test scenario

        Assertions.assertEquals(id, output.id());
        Assertions.assertEquals(CPF, output.cpf());
        Assertions.assertEquals(email, output.email());
        Assertions.assertEquals(name, output.name());
    }

    @Test
    @DisplayName("should't get customer by id")
    public void testGetByIdFail() {

        final var id = UUID.randomUUID().getMostSignificantBits();

        final var customerService = Mockito.mock(CustomerService.class);
        final var input = new GetCustomerByIdUseCase.Input(id);

        Mockito.when(customerService.findById(id)).thenReturn(Optional.empty());

        final var useCase = new GetCustomerByIdUseCase(customerService);
        final var output = useCase.execute(input);

        Assertions.assertTrue(output.isEmpty());
    }
}
