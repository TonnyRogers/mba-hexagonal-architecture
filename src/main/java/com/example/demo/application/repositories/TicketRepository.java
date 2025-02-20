package com.example.demo.application.repositories;

import java.util.Optional;

import com.example.demo.application.domain.event.ticket.Ticket;
import com.example.demo.application.domain.event.ticket.TicketId;

public interface TicketRepository {

    Optional<Ticket> ticketOfId(TicketId id);

    Ticket create(Ticket event);

    Ticket update(Ticket event);
}
