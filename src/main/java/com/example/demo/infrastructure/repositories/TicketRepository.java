package com.example.demo.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.infrastructure.models.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findByEventIdAndCustomerId(Long id, Long customerId);
}
