package com.example.demo.application.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.domain.customer.Customer;
import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.person.Cpf;
import com.example.demo.application.domain.person.Email;
import com.example.demo.application.repositories.CustomerRepository;

public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<String, Customer> customers;
    private final Map<String, Customer> customersByCPF;
    private final Map<String, Customer> customersByEmail;

    public InMemoryCustomerRepository() {
        this.customers = new HashMap<>();
        this.customersByCPF = new HashMap<>();
        this.customersByEmail = new HashMap<>();
    }

    @Override
    public Optional<Customer> customerOfId(CustomerId id) {
        return Optional.ofNullable(this.customers.get(Objects.requireNonNull(id).value()));
    }

    @Override
    public Optional<Customer> customerOfCPF(Cpf cpf) {
        return Optional.ofNullable(this.customersByCPF.get(Objects.requireNonNull(cpf.value())));
    }

    @Override
    public Optional<Customer> customerOfEmail(Email email) {
        return Optional.ofNullable(this.customersByEmail.get(Objects.requireNonNull(email.value())));
    }

    @Override
    public Customer create(Customer customer) {
        this.customers.put(customer.getCustomerId().value(), customer);
        this.customersByCPF.put(customer.getCpf().value(), customer);
        this.customersByEmail.put(customer.getEmail().value(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        this.customers.put(customer.getCustomerId().value(), customer);
        this.customersByCPF.put(customer.getCpf().value(), customer);
        this.customersByEmail.put(customer.getEmail().value(), customer);
        return customer;
    }

}
