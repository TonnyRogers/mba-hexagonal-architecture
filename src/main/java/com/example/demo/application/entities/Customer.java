package com.example.demo.application.entities;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.demo.application.exceptions.ValidationException;

public class Customer {

    private CustomerId customerId;
    private String name;
    private String cpf;
    private String email;

    public Customer(final CustomerId customerId, final String name, final String cpf, final String email) {

        if (customerId == null) {
            throw new ValidationException("Invalid customerId for Customer");
        }

        if (name == null) {
            throw new ValidationException("Invalid name for Customer");
        }

        if (cpf == null || !cpf.matches("(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)")) {
            throw new ValidationException("Invalid cpf for Customer");
        }

        if (email == null || !email.matches("(^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$)")) {
            Logger.getGlobal().log(Level.INFO, "ALOO{0}", email);
            throw new ValidationException("Invalid email for Customer");
        }

        this.customerId = customerId;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

    public static Customer newCustomer(String name, String cpf, String email) {
        return new Customer(CustomerId.unique(), name, cpf, email);
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

}
