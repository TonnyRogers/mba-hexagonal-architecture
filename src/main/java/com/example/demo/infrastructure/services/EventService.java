package com.example.demo.infrastructure.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.infrastructure.jpa.entities.EventEntity;
import com.example.demo.infrastructure.jpa.repositories.EventJpaRepository;
import com.example.demo.infrastructure.jpa.repositories.TicketJpaRepository;

@Service
public class EventService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EventJpaRepository eventRepository;

    @Autowired
    private TicketJpaRepository ticketRepository;

    @Transactional
    public EventEntity save(EventEntity event) {
        return eventRepository.save(event);
    }

    public Optional<EventEntity> findById(Long id) {
        return eventRepository.findById(id);
    }

    // public Optional<TicketEntity> findTicketByEventIdAndCustomerId(UUID id, UUID customerId) {
    //     return ticketRepository.findByEventIdAndCustomerId(id, customerId);
    // }
}
