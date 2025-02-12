package com.example.demo.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.domain.Ticket;
import com.example.demo.application.domain.TicketId;
import com.example.demo.application.repositories.TicketRepository;

public class InMemoryTicketRepository implements TicketRepository {

    private final Map<String, Ticket> tickets;

    public InMemoryTicketRepository() {
        this.tickets = new HashMap<>();
    }

    @Override
    public Optional<Ticket> ticketOfId(TicketId id) {
        return Optional.ofNullable(this.tickets.get(Objects.requireNonNull(id).value()));
    }

    @Override
    public Ticket create(Ticket ticket) {
        this.tickets.put(ticket.getTicketId().value(), ticket);
        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        this.tickets.put(ticket.getTicketId().value(), ticket);
        return ticket;
    }

}
