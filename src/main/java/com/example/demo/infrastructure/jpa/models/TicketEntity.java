package com.example.demo.infrastructure.jpa.models;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.event.EventId;
import com.example.demo.application.domain.event.ticket.Ticket;
import com.example.demo.application.domain.event.ticket.TicketId;
import com.example.demo.application.domain.event.ticket.TicketStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    private UUID id;

    private UUID customerId;

    private UUID eventId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private Instant paidAt;

    private Instant reservedAt;

    public TicketEntity() {
    }

    public TicketEntity(
            UUID id,
            UUID customerId,
            UUID eventId,
            TicketStatus status,
            Instant paidAt,
            Instant reservedAt) {
        this.id = id;
        this.customerId = customerId;
        this.eventId = eventId;
        this.status = status;
        this.paidAt = paidAt;
        this.reservedAt = reservedAt;
    }

    public static TicketEntity of(Ticket ticket) {
        return new TicketEntity(
                UUID.fromString(ticket.getTicketId().value()),
                UUID.fromString(ticket.getCustomerId().value()),
                UUID.fromString(ticket.getEventId().value()),
                ticket.getStatus(),
                ticket.getPaidAt(),
                ticket.getReservedAt()
        );
    }

    public Ticket toTicket() {
        return new Ticket(
                TicketId.with(this.id.toString()),
                CustomerId.with(this.customerId.toString()),
                EventId.with(this.eventId.toString()),
                this.status,
                this.paidAt,
                this.reservedAt
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomer() {
        return customerId;
    }

    public void setCustomer(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getEvent() {
        return eventId;
    }

    public void setEvent(UUID eventId) {
        this.eventId = eventId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    public Instant getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(Instant reservedAt) {
        this.reservedAt = reservedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketEntity ticket = (TicketEntity) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, eventId);
    }
}
