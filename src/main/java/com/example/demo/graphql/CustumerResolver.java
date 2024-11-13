package com.example.demo.graphql;

import java.util.Objects;

import org.apache.juli.logging.Log;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.dtos.CustomerDTO;
import com.example.demo.models.Customer;
import com.example.demo.services.CustomerService;

@Controller
public class CustumerResolver {

    private final CustomerService customerService;

    public CustumerResolver(final CustomerService customerService) {
        this.customerService = Objects.requireNonNull(customerService);
    }

    @MutationMapping
    public CustomerDTO createCustomer(@Argument CustomerDTO input) {
        if (customerService.findByCpf(input.getCpf()).isPresent()) {
            throw new RuntimeException("Customer already exists");
        }
        if (customerService.findByEmail(input.getEmail()).isPresent()) {
            throw new RuntimeException("Customer already exists");
        }

        var customer = new Customer();
        customer.setName(input.getName());
        customer.setCpf(input.getCpf());
        customer.setEmail(input.getEmail());

        customer = customerService.save(customer);

        return new CustomerDTO(customer);
    }

    @QueryMapping
    public CustomerDTO customerOfId(@Argument Long id) {
        return customerService.findById(id)
                .map(CustomerDTO::new)
                .orElse(null);
    }
}
