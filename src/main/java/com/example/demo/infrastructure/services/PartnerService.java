package com.example.demo.infrastructure.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.infrastructure.jpa.entities.PartnerEntity;
import com.example.demo.infrastructure.jpa.repositories.PartnerJpaRepository;

@Service
public class PartnerService {

    @Autowired
    private PartnerJpaRepository repository;

    @Transactional
    public PartnerEntity save(PartnerEntity customer) {
        return repository.save(customer);
    }

    public Optional<PartnerEntity> findById(UUID id) {
        return repository.findById(id);
    }

    public Optional<PartnerEntity> findByCnpj(String cnpj) {
        return repository.findByCnpj(cnpj);
    }

    public Optional<PartnerEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
