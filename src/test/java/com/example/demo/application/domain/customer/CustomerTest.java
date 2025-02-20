package com.example.demo.application.domain.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.exceptions.ValidationException;

public class CustomerTest {

    @Test
    @DisplayName("should create a customer instance")
    public void testCreateCustomerInstance() {
        // given
        final var CPF = "265.359.485-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        // when
        final var actualCustomer = Customer.newCustomer(name, CPF, email);

        //then
        Assertions.assertNotNull(actualCustomer.getCustomerId());
        Assertions.assertEquals(CPF, actualCustomer.getCpf().value());
        Assertions.assertEquals(email, actualCustomer.getEmail().value());
        Assertions.assertEquals(name, actualCustomer.getName().value());
    }

    @Test
    @DisplayName("should`n create a customer instance with invalid CPF")
    public void testCreateCustomerInstanceInValidCPF() {
        // given
        final var CPF = "265359.485-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var expectedError = "Invalid value for CPF";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Customer.newCustomer(name, CPF, email)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("should`n create a customer instance with invalid name")
    public void testCreateCustomerInstanceInValidName() {
        // given
        final var CPF = "265.359.485-55";
        final var email = "teste@test.com";
        final var expectedError = "Invalid name for Customer";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Customer.newCustomer(null, CPF, email)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("should`n create a customer instance with invalid email")
    public void testCreateCustomerInstanceInValidEmail() {
        // given
        final var CPF = "265.359.485-55";
        final var name = "Joao";
        final var expectedError = "Invalid value for Email";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Customer.newCustomer(name, CPF, null)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
