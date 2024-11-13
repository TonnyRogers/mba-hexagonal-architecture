package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

}
