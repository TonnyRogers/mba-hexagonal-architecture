package com.example.demo.application.usecases.customer;

import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.repositories.CustomerRepository;
import com.example.demo.application.usecases.UseCase;

public class GetCustomerByIdUseCase extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {

    private final CustomerRepository customerRepository;

    public GetCustomerByIdUseCase(final CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return customerRepository.customerOfId(CustomerId.with(input.id))
                .map(c -> new Output(
                c.getCustomerId().value(),
                c.getCpf().value(),
                c.getEmail().value(),
                c.getName().value()
        ));
    }

    public record Input(String id) {

    }

    public record Output(String id, String cpf, String email, String name) {

    }

}
