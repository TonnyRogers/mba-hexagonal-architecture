package com.example.demo.infrastructure.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.infrastructure.models.Event;
import com.example.demo.infrastructure.models.Ticket;
import com.example.demo.infrastructure.repositories.EventRepository;
import com.example.demo.infrastructure.repositories.TicketRepository;

@Service
public class EventService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public Optional<Ticket> findTicketByEventIdAndCustomerId(Long id, Long customerId) {
        return ticketRepository.findByEventIdAndCustomerId(id, customerId);
    }
}
