package com.example.demo.graphql;

import java.util.Objects;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.application.usecases.CreateCustomerUseCase;
import com.example.demo.dtos.CustomerDTO;
import com.example.demo.services.CustomerService;

@Controller
public class CustumerResolver {

    private final CustomerService customerService;

    public CustumerResolver(final CustomerService customerService) {
        this.customerService = Objects.requireNonNull(customerService);
    }

    @MutationMapping
    public CreateCustomerUseCase.Output createCustomer(@Argument CustomerDTO input) {
        final var useCase = new CreateCustomerUseCase(customerService);
        final var output = useCase.execute(new CreateCustomerUseCase.Input(input.getCpf(), input.getEmail(), input.getName()));

        return output;
    }

    @QueryMapping
    public CustomerDTO customerOfId(@Argument Long id) {
        return customerService.findById(id)
                .map(CustomerDTO::new)
                .orElse(null);
    }
}
