package com.example.demo.application.domain.person;

import com.example.demo.application.exceptions.ValidationException;

public record Cnpj(String value) {

    public Cnpj {

        if (value == null || !value.matches("(^\\d{2}\\x2E\\d{3}\\x2E\\d{3}\\/\\d{4}\\x2D\\d{2}$)")) {
            throw new ValidationException("Invalid value for CNPJ");
        }
    }
}
