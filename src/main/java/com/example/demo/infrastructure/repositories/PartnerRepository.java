package com.example.demo.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.infrastructure.models.Partner;

public interface PartnerRepository extends CrudRepository<Partner, Long> {

    Optional<Partner> findByCnpj(String cnpj);

    Optional<Partner> findByEmail(String email);
}
