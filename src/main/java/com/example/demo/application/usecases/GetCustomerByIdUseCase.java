package com.example.demo.application.usecases;

import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.UseCase;
import com.example.demo.infrastructure.services.CustomerService;

public class GetCustomerByIdUseCase extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {

    private final CustomerService customerService;

    public GetCustomerByIdUseCase(final CustomerService customerService1) {
        this.customerService = Objects.requireNonNull(customerService1);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return customerService.findById(input.id)
                .map(c -> new Output(c.getId(), c.getCpf(), c.getEmail(), c.getName()));
    }

    public record Input(Long id) {

    }

    public record Output(Long id, String cpf, String email, String name) {

    }

}
