package com.example.demo.infrastructure.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.infrastructure.jpa.entities.CustomerEntity;

public interface CustomerJpaRepository extends CrudRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByCpf(String cpf);

    Optional<CustomerEntity> findByEmail(String email);
}
