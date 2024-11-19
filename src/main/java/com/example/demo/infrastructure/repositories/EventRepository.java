package com.example.demo.infrastructure.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.infrastructure.models.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

}
