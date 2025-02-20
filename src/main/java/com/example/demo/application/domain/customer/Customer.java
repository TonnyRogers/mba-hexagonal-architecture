package com.example.demo.application.domain.customer;

import java.util.Objects;

import com.example.demo.application.domain.person.Cpf;
import com.example.demo.application.domain.person.Email;
import com.example.demo.application.domain.person.Name;
import com.example.demo.application.exceptions.ValidationException;

public class Customer {

    private final CustomerId customerId;
    private Name name;
    private Cpf cpf;
    private Email email;

    public Customer(final CustomerId customerId, final String name, final String cpf, final String email) {

        if (customerId == null) {
            throw new ValidationException("Invalid customerId for Customer");
        }

        if (name == null) {
            throw new ValidationException("Invalid name for Customer");
        }

        this.customerId = customerId;
        this.setName(name);
        this.setCpf(cpf);
        this.setEmail(email);
    }

    public static Customer newCustomer(String name, String cpf, String email) {
        return new Customer(CustomerId.unique(), name, cpf, email);
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Name getName() {
        return name;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    private void setCpf(final String cpf) {
        this.cpf = new Cpf(cpf);
    }

    private void setName(String name) {
        this.name = new Name(name);
    }

    private void setEmail(String email) {
        this.email = new Email(email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Customer customer = (Customer) obj;

        return Objects.equals(customerId, customer.customerId);
    }

}
