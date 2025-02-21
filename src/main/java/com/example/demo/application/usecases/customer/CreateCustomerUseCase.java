package com.example.demo.application.usecases.customer;

import java.util.Objects;

import com.example.demo.application.domain.customer.Customer;
import com.example.demo.application.domain.person.Cpf;
import com.example.demo.application.domain.person.Email;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.application.repositories.CustomerRepository;
import com.example.demo.application.usecases.UseCase;

public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.Input, CreateCustomerUseCase.Output> {

    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(final CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Output execute(Input input) {
        if (customerRepository.customerOfCPF(new Cpf(input.cpf)).isPresent()
                || customerRepository.customerOfEmail(new Email(input.email)).isPresent()) {
            throw new ValidationException("Customer already exists");
        }

        var customer = customerRepository.create(Customer.newCustomer(input.name, input.cpf, input.email));

        return new Output(
                customer.getCustomerId().value(),
                customer.getCpf().value(),
                customer.getEmail().value(),
                customer.getName().value()
        );
    }

    public record Input(String cpf, String email, String name) {

    }

    public record Output(String id, String cpf, String email, String name) {

    }

}
