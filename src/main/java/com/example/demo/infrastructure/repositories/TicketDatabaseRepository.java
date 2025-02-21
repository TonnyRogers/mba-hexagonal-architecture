package com.example.demo.infrastructure.repositories;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.application.domain.event.ticket.Ticket;
import com.example.demo.application.domain.event.ticket.TicketId;
import com.example.demo.application.repositories.TicketRepository;
import com.example.demo.infrastructure.jpa.models.TicketEntity;
import com.example.demo.infrastructure.jpa.repositories.TicketJpaRepository;

// interface adapter
@Component
public class TicketDatabaseRepository implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;

    public TicketDatabaseRepository(final TicketJpaRepository ticketJpaRepository) {
        this.ticketJpaRepository = Objects.requireNonNull(ticketJpaRepository);
    }

    @Override
    public Optional<Ticket> ticketOfId(TicketId id) {
        Objects.requireNonNull(id, "id cannot be null");
        return this.ticketJpaRepository.findById(UUID.fromString(id.value()))
                .map(TicketEntity::toTicket);
    }

    @Override
    @Transactional
    public Ticket create(Ticket ticket) {
        return this.ticketJpaRepository.save(TicketEntity.of(ticket))
                .toTicket();
    }

    @Override
    @Transactional
    public Ticket update(Ticket ticket) {
        return this.ticketJpaRepository.save(TicketEntity.of(ticket))
                .toTicket();
    }

}
