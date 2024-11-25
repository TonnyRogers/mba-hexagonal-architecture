package com.example.demo.application.entities;

import com.example.demo.application.exceptions.ValidationException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("(^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$)")) {
            throw new ValidationException("Invalid value for Email");
        }
    }
}
