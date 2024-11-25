package com.example.demo.application.entities;

import java.util.UUID;

import com.example.demo.application.exceptions.ValidationException;

public record CustomerId(UUID value) {

    public static CustomerId unique() {
        return new CustomerId(UUID.randomUUID());
    }

    public static CustomerId with(final String value) {
        try {
            return new CustomerId(UUID.fromString(value));
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for CustomerId");
        }
    }
}