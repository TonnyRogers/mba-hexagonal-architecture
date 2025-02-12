package com.example.demo.application.repositories;

import java.util.Optional;

import com.example.demo.application.domain.Event;
import com.example.demo.application.domain.EventId;

public interface EventRepository {

    Optional<Event> eventOfId(EventId id);

    Event create(Event event);

    Event update(Event event);
}
