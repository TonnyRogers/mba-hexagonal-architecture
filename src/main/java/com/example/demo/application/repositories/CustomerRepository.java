package com.example.demo.application.repositories;

import java.util.Optional;

import com.example.demo.application.domain.customer.Customer;
import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.person.Cpf;
import com.example.demo.application.domain.person.Email;

public interface CustomerRepository {

    Optional<Customer> customerOfId(CustomerId id);

    Optional<Customer> customerOfCPF(Cpf CPF);

    Optional<Customer> customerOfEmail(Email Email);

    Customer create(Customer customer);

    Customer update(Customer customer);
}
