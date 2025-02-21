package com.example.demo.infrastructure.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.infrastructure.jpa.models.PartnerEntity;

public interface PartnerJpaRepository extends CrudRepository<PartnerEntity, UUID> {

    Optional<PartnerEntity> findByCnpj(String cnpj);

    Optional<PartnerEntity> findByEmail(String email);
}
