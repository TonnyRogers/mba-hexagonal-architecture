package com.example.demo.infrastructure.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.infrastructure.models.Partner;
import com.example.demo.infrastructure.repositories.PartnerRepository;

@Service
public class PartnerService {

    @Autowired
    private PartnerRepository repository;

    @Transactional
    public Partner save(Partner customer) {
        return repository.save(customer);
    }

    public Optional<Partner> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Partner> findByCnpj(String cnpj) {
        return repository.findByCnpj(cnpj);
    }

    public Optional<Partner> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
