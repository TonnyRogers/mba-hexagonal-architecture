package com.example.demo.infrastructure.repositories;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.application.domain.event.Event;
import com.example.demo.application.domain.event.EventId;
import com.example.demo.application.repositories.EventRepository;
import com.example.demo.infrastructure.jpa.entities.EventEntity;
import com.example.demo.infrastructure.jpa.repositories.EventJpaRepository;

// interface adapter
@Component
public class EventDatabaseRepository implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    public EventDatabaseRepository(final EventJpaRepository eventJpaRepository) {
        this.eventJpaRepository = Objects.requireNonNull(eventJpaRepository);
    }

    @Override
    public Optional<Event> eventOfId(EventId id) {
        Objects.requireNonNull(id, "id cannot be null");
        return this.eventJpaRepository.findById(UUID.fromString(id.value()))
                .map(EventEntity::toEvent);
    }

    @Override
    @Transactional
    public Event create(Event event) {
        return this.eventJpaRepository.save(EventEntity.of(event))
                .toEvent();
    }

    @Override
    @Transactional
    public Event update(Event event) {
        return this.eventJpaRepository.save(EventEntity.of(event))
                .toEvent();
    }

}
