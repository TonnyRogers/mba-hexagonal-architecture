package com.example.demo.application.repositories;

import java.util.Optional;

import com.example.demo.application.domain.customer.Customer;
import com.example.demo.application.domain.customer.CustomerId;

public interface CustomerRepository {

    Optional<Customer> customerOfId(CustomerId id);

    Optional<Customer> customerOfCPF(String CPF);

    Optional<Customer> customerOfEmail(String Email);

    Customer create(Customer customer);

    Customer update(Customer customer);
}
