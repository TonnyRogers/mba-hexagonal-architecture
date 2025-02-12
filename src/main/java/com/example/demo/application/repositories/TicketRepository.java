package com.example.demo.application.repositories;

import java.util.Optional;

import com.example.demo.application.domain.Ticket;
import com.example.demo.application.domain.TicketId;

public interface TicketRepository {

    Optional<Ticket> ticketOfId(TicketId id);

    Ticket create(Ticket event);

    Ticket update(Ticket event);
}
