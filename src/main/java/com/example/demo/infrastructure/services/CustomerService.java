package com.example.demo.infrastructure.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.infrastructure.models.Customer;
import com.example.demo.infrastructure.repositories.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Transactional
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Customer> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
