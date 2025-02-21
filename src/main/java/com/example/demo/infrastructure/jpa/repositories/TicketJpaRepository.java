package com.example.demo.infrastructure.jpa.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.infrastructure.jpa.models.TicketEntity;

public interface TicketJpaRepository extends CrudRepository<TicketEntity, UUID> {

}
