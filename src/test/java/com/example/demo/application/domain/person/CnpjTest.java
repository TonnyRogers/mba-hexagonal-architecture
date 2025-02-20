package com.example.demo.application.domain.person;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.exceptions.ValidationException;

public class CnpjTest {

    @Test
    @DisplayName("should create CNPJ")
    public void testCreateCNPJ() {
        // given
        final var CNPJ = "01.738.501/0001-32";

        // when
        final var cnpjCreated = new Cnpj(CNPJ);

        //then
        Assertions.assertEquals(CNPJ, cnpjCreated.value());
    }

    @Test
    @DisplayName("should`n create with nullable CNPJ")
    public void testCreateNullabledCNPJ() {
        // given
        final var expectedError = "Invalid value for CNPJ";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Cnpj(null)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("should`n create with invalid CNPJ")
    public void testCreateInValidCNPJ() {
        // given
        final var CNPJ = "01.738.501/000132";
        final var expectedError = "Invalid value for CNPJ";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Cnpj(CNPJ)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
