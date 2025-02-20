package com.example.demo.application.domain.person;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.exceptions.ValidationException;

public class EmailTest {

    @Test
    @DisplayName("should create email")
    public void testCreateEmail() {
        // given
        final var email = "teste@test.com";

        // when
        final var emailCreated = new Email(email);

        //then
        Assertions.assertEquals(email, emailCreated.value());
    }

    @Test
    @DisplayName("should`n create with nullable email")
    public void testCreateNullabledEmail() {
        // given
        final var expectedError = "Invalid value for Email";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Email(null)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("should`n create with invalid email")
    public void testCreateInValidemail() {
        // given
        final var email = "265.359.48555";
        final var expectedError = "Invalid value for Email";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Email(email)
        );

        //then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
