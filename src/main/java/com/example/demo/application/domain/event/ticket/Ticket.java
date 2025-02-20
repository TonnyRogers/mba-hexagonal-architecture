package com.example.demo.application.domain.event.ticket;

import java.time.Instant;

import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.event.EventId;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.models.TicketStatus;

public class Ticket {

    private final TicketId ticketId;
    private CustomerId customerId;
    private EventId eventId;
    private TicketStatus status;
    private Instant paidAt;
    private Instant reservedAt;

    public Ticket(
            TicketId ticketId,
            CustomerId customerId,
            EventId eventId,
            TicketStatus status,
            Instant paidAt,
            Instant reservedAt) {
        this.ticketId = ticketId;
        this.setCustomerId(customerId);
        this.setEventId(eventId);
        this.setStatus(status);
        this.setPaidAt(paidAt);
        this.setReservedAt(reservedAt);
    }

    public static Ticket newTicket(final CustomerId customerId, final EventId eventId) {
        return new Ticket(TicketId.unique(), customerId, eventId, TicketStatus.PENDING, null, Instant.now());
    }

    public TicketId getTicketId() {
        return ticketId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public EventId getEventId() {
        return eventId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public Instant getReservedAt() {
        return reservedAt;
    }

    private void setCustomerId(CustomerId customerId) {
        if (customerId == null) {
            throw new ValidationException("Invalid customerId for Ticket");
        }
        this.customerId = customerId;
    }

    private void setEventId(EventId eventId) {
        if (eventId == null) {
            throw new ValidationException("Invalid value eventId Ticket");
        }
        this.eventId = eventId;
    }

    private void setStatus(TicketStatus status) {
        if (status == null) {
            throw new ValidationException("Invalid status for Ticket");
        }
        this.status = status;
    }

    private void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    private void setReservedAt(Instant reservedAt) {
        if (reservedAt == null) {
            throw new ValidationException("Invalid reservedAt for Ticket");
        }
        this.reservedAt = reservedAt;
    }

}
