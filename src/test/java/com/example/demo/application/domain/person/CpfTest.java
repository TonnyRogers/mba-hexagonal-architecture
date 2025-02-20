package com.example.demo.application.domain.person;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.exceptions.ValidationException;

public class CpfTest {

    @Test
    @DisplayName("should create CPF")
    public void testCreateCPF() {
        // given
        final var CPF = "265.359.485-55";

        // when
        final var cnpjCreated = new Cpf(CPF);

        //then
        Assertions.assertEquals(CPF, cnpjCreated.value());
    }

    @Test
    @DisplayName("should`n create with nullable CPF")
    public void testCreateNullabledCPF() {
        // given
        final var expectedError = "Invalid value for CPF";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Cpf(null)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("should`n create with invalid CPF")
    public void testCreateInValidCPF() {
        // given
        final var CPF = "265.359.48555";
        final var expectedError = "Invalid value for CPF";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Cpf(CPF)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
