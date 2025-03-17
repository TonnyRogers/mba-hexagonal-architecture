package com.example.demo.application.domain.event;

import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.event.ticket.TicketId;
import com.example.demo.application.exceptions.ValidationException;

public class EventTicket {

    private final TicketId ticketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private int ordering;

    public EventTicket(
            final TicketId ticketId,
            final EventId eventId,
            final CustomerId customerId,
            final Integer ordering
    ) {
        if (ticketId == null) {
            throw new ValidationException("Invalid ticketId para EventTicket");
        }

        if (eventId == null) {
            throw new ValidationException("Invalid eventId para EventTicket");
        }

        if (customerId == null) {
            throw new ValidationException("Invalid customerId para EventTicket");
        }

        this.ticketId = ticketId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.setOrdering(ordering);
    }

    private void setOrdering(final Integer ordering) {
        if (ordering == null) {
            throw new ValidationException("");
        }

        this.ordering = ordering;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public TicketId getTicketId() {
        return ticketId;
    }

    public EventId getEventId() {
        return eventId;
    }

    public int getOrdering() {
        return ordering;
    }
}
