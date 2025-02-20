package com.example.demo.application.domain.partner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.exceptions.ValidationException;

public class PartnerTest {

    @Test
    @DisplayName("should create a partner instance")
    public void testCreatePartnerInstance() {
        // given
        final var CNPJ = "01.738.501/0001-32";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        // when
        final var actualPartner = Partner.newPartner(name, CNPJ, email);

        //then
        Assertions.assertNotNull(actualPartner.getPartnerId());
        Assertions.assertEquals(CNPJ, actualPartner.getCnpj().value());
        Assertions.assertEquals(email, actualPartner.getEmail().value());
        Assertions.assertEquals(name, actualPartner.getName().value());
    }

    @Test
    @DisplayName("should`n create a partner instance with invalid CNPJ")
    public void testCreatePartnerInstanceInValidCNPJ() {
        // given
        final var CNPJ = "01.738.501/000132";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var expectedError = "Invalid value for CNPJ";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Partner.newPartner(name, CNPJ, email)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("should`n create a partner instance with invalid name")
    public void testCreatePartnerInstanceInValidName() {
        // given
        final var CNPJ = "01.738.501/0001-32";
        final var email = "teste@test.com";
        final var expectedError = "Invalid value for Name";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Partner.newPartner(null, CNPJ, email)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("should`n create a partner instance with invalid email")
    public void testCreatePartnerInstanceInValidEmail() {
        // given
        final var CNPJ = "01.738.501/0001-32";
        final var name = "Joao";
        final var expectedError = "Invalid value for Email";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Partner.newPartner(name, CNPJ, null)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
