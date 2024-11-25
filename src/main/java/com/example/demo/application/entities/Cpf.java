package com.example.demo.application.entities;

import com.example.demo.application.exceptions.ValidationException;

public record Cpf(String value) {

    public Cpf {
        if (value == null || !value.matches("(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)")) {
            throw new ValidationException("Invalid value for CPF");
        }
    }
}
