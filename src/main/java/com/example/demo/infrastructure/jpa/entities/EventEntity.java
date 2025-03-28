package com.example.demo.infrastructure.jpa.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.demo.application.domain.event.Event;
import com.example.demo.application.domain.event.EventTicket;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    private UUID id;

    private String name;

    private LocalDate date;

    private int totalSpots;

    private UUID partner;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
    private Set<EventTicketEntity> tickets;

    public EventEntity() {
        this.tickets = new HashSet<>();
    }

    public EventEntity(UUID id, String name, LocalDate date, int totalSpots, UUID partnerId) {
        this();
        this.id = id;
        this.name = name;
        this.date = date;
        this.totalSpots = totalSpots;
        this.partner = partnerId;
    }

    public static EventEntity of(final Event event) {
        final var entity = new EventEntity(
                UUID.fromString(event.getEventId().value()),
                event.getName().value(),
                event.getDate(),
                event.getTotalSpots(),
                UUID.fromString(event.getPartnerId().value())
        );

        event.getAllTickets().forEach(entity::addTicket);

        return entity;
    }

    public Event toEvent() {
        return Event.restore(
                this.id.toString(),
                this.getName(),
                this.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                this.getTotalSpots(),
                this.getPartner().toString(),
                this.getTickets().stream().map(EventTicketEntity::toEventTicket)
                        .collect(Collectors.toSet())
        );
    }

    private void addTicket(final EventTicket ticket) {
        this.tickets.add(EventTicketEntity.of(this, ticket));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public UUID getPartner() {
        return partner;
    }

    public void setPartner(UUID partnerId) {
        this.partner = partnerId;
    }

    public Set<EventTicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(Set<EventTicketEntity> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventEntity event = (EventEntity) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
