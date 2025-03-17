package com.example.demo.infrastructure.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.infrastructure.jpa.entities.CustomerEntity;
import com.example.demo.infrastructure.jpa.repositories.CustomerJpaRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerJpaRepository repository;

    @Transactional
    public CustomerEntity save(CustomerEntity customer) {
        return repository.save(customer);
    }

    public Optional<CustomerEntity> findById(UUID id) {
        return repository.findById(id);
    }

    public Optional<CustomerEntity> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public Optional<CustomerEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
