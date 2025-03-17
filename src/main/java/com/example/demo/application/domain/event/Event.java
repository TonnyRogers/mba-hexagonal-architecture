package com.example.demo.application.domain.event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.event.ticket.Ticket;
import com.example.demo.application.domain.partner.Partner;
import com.example.demo.application.domain.partner.PartnerId;
import com.example.demo.application.domain.person.Name;
import com.example.demo.application.exceptions.ValidationException;

public class Event {

    public final EventId eventId;
    public Name name;
    public LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;
    private Set<EventTicket> tickets;

    public Event(
            EventId eventId,
            String name,
            String date,
            Integer totalSpots,
            PartnerId partnerId,
            Set<EventTicket> tickets
    ) {

        this(eventId, tickets);
        this.setName(name);
        this.setDate(date);
        this.setTotalSpots(totalSpots);
        this.setPartnerId(partnerId);
    }

    private Event(final EventId eventId, final Set<EventTicket> tickets) {
        if (eventId == null) {
            throw new ValidationException("Invalid eventId for Event");
        }

        this.eventId = eventId;
        this.tickets = tickets != null ? tickets : new HashSet<>(0);
    }

    public static Event newEvent(
            final String name,
            final String date,
            final Integer totalSpots,
            final Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.getPartnerId(), null);
    }

    public Ticket reserveTicket(final CustomerId customerId) {
        this.getAllTickets().stream()
                .filter(it -> Objects.equals(it.getCustomerId(), customerId))
                .findFirst()
                .ifPresent(it -> {
                    throw new ValidationException("Email already registered");
                });
        if (this.getTotalSpots() < this.getAllTickets().size() + 1) {
            throw new ValidationException("Event sold out");
        }

        final var newTicket = Ticket.newTicket(customerId, this.getEventId());
        this.tickets.add(
                new EventTicket(newTicket.getTicketId(), eventId, customerId, this.getAllTickets().size() + 1)
        );

        return newTicket;
    }

    public EventId getEventId() {
        return eventId;
    }

    public Name getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public PartnerId getPartnerId() {
        return partnerId;
    }

    public Set<EventTicket> getAllTickets() {
        return Collections.unmodifiableSet(tickets);
    }

    private void setName(final String name) {
        if (name == null) {
            throw new ValidationException("Invalid name for Event");
        }

        this.name = new Name(name);
    }

    private void setDate(final String date) {
        if (date == null) {
            throw new ValidationException("Invalid date for Event.");
        }

        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            throw new ValidationException("Invalid date for Event.");
        }
    }

    private void setTotalSpots(final Integer totalSpots) {
        if (totalSpots == null) {
            throw new ValidationException("Invalid totalSpots for Event");
        }

        this.totalSpots = totalSpots;
    }

    private void setPartnerId(final PartnerId partnerId) {
        if (partnerId == null) {
            throw new ValidationException("Invalid partnerId for Event");
        }

        this.partnerId = partnerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Event event = (Event) obj;

        return Objects.equals(eventId, event.eventId);
    }

    public static Event restore(
            final String id,
            final String name,
            final String date,
            final int totalSpots,
            final String partner,
            final Set<EventTicket> tickets
    ) {
        return new Event(
                EventId.with(id),
                name,
                date,
                totalSpots,
                PartnerId.with(partner),
                tickets
        );
    }

}
