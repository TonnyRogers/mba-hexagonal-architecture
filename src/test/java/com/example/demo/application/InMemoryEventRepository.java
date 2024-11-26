package com.example.demo.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.entities.Event;
import com.example.demo.application.entities.EventId;
import com.example.demo.application.repositories.EventRepository;

public class InMemoryEventRepository implements EventRepository {

    private final Map<String, Event> events;

    public InMemoryEventRepository() {
        this.events = new HashMap<>();
    }

    @Override
    public Optional<Event> eventOfId(EventId id) {
        return Optional.ofNullable(this.events.get(Objects.requireNonNull(id).value()));
    }

    @Override
    public Event create(Event event) {
        this.events.put(event.getEventId().value(), event);
        return event;
    }

    @Override
    public Event update(Event event) {
        this.events.put(event.getEventId().value(), event);
        return event;
    }

}
