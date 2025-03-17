package com.example.demo.infrastructure.jpa.entities;

import java.util.Objects;
import java.util.UUID;

import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.event.EventId;
import com.example.demo.application.domain.event.EventTicket;
import com.example.demo.application.domain.event.ticket.TicketId;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_ticket")
public class EventTicketEntity {

    @Id
    private UUID ticketId;

    private UUID customerId;

    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventEntity event;

    public EventTicketEntity(
            UUID ticketId,
            UUID customerId,
            EventEntity event,
            int ordering
    ) {
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.event = event;
        this.ordering = ordering;
    }

    public static EventTicketEntity of(final EventEntity event, final EventTicket eventTicket) {
        return new EventTicketEntity(
                UUID.fromString(eventTicket.getTicketId().value()),
                UUID.fromString(eventTicket.getCustomerId().value()),
                event,
                eventTicket.getOrdering()
        );
    }

    public EventTicket toEventTicket() {
        return new EventTicket(
                TicketId.with(this.getTicketId().toString()),
                EventId.with(this.getEvent().getId().toString()),
                CustomerId.with(this.getcustomerId().toString()),
                this.getOrdering()
        );
    }

    public UUID getTicketId() {
        return this.ticketId;
    }

    public UUID getcustomerId() {
        return this.customerId;
    }

    public int getOrdering() {
        return this.ordering;
    }

    public EventEntity getEvent() {
        return this.event;
    }

    public void getTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public void getcustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void getOrdering(int ordering) {
        this.ordering = ordering;
    }

    public void getEvent(EventEntity eventTicket) {
        this.event = eventTicket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventTicketEntity that = (EventTicketEntity) o;
        return ordering == that.ordering && Objects.equals(ticketId, that.ticketId)
                && Objects.equals(customerId, that.customerId) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, customerId, event, ordering);
    }
}
