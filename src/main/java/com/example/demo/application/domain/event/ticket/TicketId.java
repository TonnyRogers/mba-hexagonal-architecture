package com.example.demo.application.domain.event.ticket;

import java.util.UUID;

import com.example.demo.application.exceptions.ValidationException;

public record TicketId(String value) {

    public TicketId {
        if (value == null) {
            throw new ValidationException("Invalid value for TicketId");
        }
    }

    public static TicketId unique() {
        return new TicketId(UUID.randomUUID().toString());
    }

    public static TicketId with(final String value) {
        try {
            return new TicketId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for TicketId");
        }
    }
}
