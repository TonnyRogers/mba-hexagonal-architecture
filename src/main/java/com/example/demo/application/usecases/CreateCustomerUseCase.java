package com.example.demo.application.usecases;

import java.util.Objects;

import com.example.demo.application.UseCase;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.models.Customer;
import com.example.demo.infrastructure.services.CustomerService;

public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.Input, CreateCustomerUseCase.Output> {

    private final CustomerService customerService;

    public CreateCustomerUseCase(final CustomerService customerService1) {
        this.customerService = Objects.requireNonNull(customerService1);
    }

    @Override
    public Output execute(Input input) {
        if (customerService.findByCpf(input.cpf).isPresent() || customerService.findByEmail(input.email).isPresent()) {
            throw new ValidationException("Customer already exists");
        }

        var customer = new Customer();
        customer.setName(input.name);
        customer.setCpf(input.cpf);
        customer.setEmail(input.email);

        customer = customerService.save(customer);

        return new Output(customer.getId(), customer.getCpf(), customer.getEmail(), customer.getName());
    }

    public record Input(String cpf, String email, String name) {

    }

    public record Output(Long id, String cpf, String email, String name) {

    }

}
