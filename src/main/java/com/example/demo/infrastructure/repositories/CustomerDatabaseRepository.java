package com.example.demo.infrastructure.repositories;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.application.domain.customer.Customer;
import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.person.Cpf;
import com.example.demo.application.domain.person.Email;
import com.example.demo.application.repositories.CustomerRepository;
import com.example.demo.infrastructure.jpa.models.CustomerEntity;
import com.example.demo.infrastructure.jpa.repositories.CustomerJpaRepository;

// interface adapter
@Component
public class CustomerDatabaseRepository implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerDatabaseRepository(final CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = Objects.requireNonNull(customerJpaRepository);
    }

    @Override
    public Optional<Customer> customerOfId(CustomerId id) {
        Objects.requireNonNull(id, "id cannot be null");
        return this.customerJpaRepository.findById(UUID.fromString(id.value()))
                .map(CustomerEntity::toCustomer);
    }

    @Override
    public Optional<Customer> customerOfCPF(Cpf cpf) {
        Objects.requireNonNull(cpf, "Cpf cannot be null");
        return this.customerJpaRepository.findByCpf(cpf.value())
                .map(CustomerEntity::toCustomer);
    }

    @Override
    public Optional<Customer> customerOfEmail(Email email) {
        Objects.requireNonNull(email, "email cannot be null");
        return this.customerJpaRepository.findByEmail(email.value())
                .map(CustomerEntity::toCustomer);
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        return this.customerJpaRepository.save(CustomerEntity.of(customer))
                .toCustomer();
    }

    @Override
    public Customer update(Customer customer) {
        return this.customerJpaRepository.save(CustomerEntity.of(customer))
                .toCustomer();
    }

}
