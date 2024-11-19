package com.example.demo.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.infrastructure.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByCpf(String cpf);

    Optional<Customer> findByEmail(String email);
}
