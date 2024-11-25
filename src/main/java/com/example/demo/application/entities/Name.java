package com.example.demo.application.entities;

import com.example.demo.application.exceptions.ValidationException;

public record Name(String value) {

    public Name {

        if (value == null) {
            throw new ValidationException("Invalid value for Name");
        }
    }
}
