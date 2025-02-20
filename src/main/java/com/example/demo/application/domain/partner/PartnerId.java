package com.example.demo.application.domain.partner;

import java.util.UUID;

import com.example.demo.application.exceptions.ValidationException;

public record PartnerId(String value) {

    public PartnerId {
        if (value == null) {
            throw new ValidationException("Invalid value for PartnerId");
        }
    }

    public static PartnerId unique() {
        return new PartnerId(UUID.randomUUID().toString());
    }

    public static PartnerId with(final String value) {
        try {
            return new PartnerId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for PartnerId");
        }
    }
}
