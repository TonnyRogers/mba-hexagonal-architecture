package com.example.demo.infrastructure.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.infrastructure.jpa.models.EventEntity;

public interface EventJpaRepository extends CrudRepository<EventEntity, Long> {

}
