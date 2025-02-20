package com.example.demo.infrastructure.configurations;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.application.usecases.customer.CreateCustomerUseCase;
import com.example.demo.application.usecases.customer.GetCustomerByIdUseCase;
import com.example.demo.application.usecases.event.CreateEventUseCase;
import com.example.demo.application.usecases.event.SubscribeCustomerToEventUseCase;
import com.example.demo.application.usecases.partner.CreatePartnerUseCase;
import com.example.demo.application.usecases.partner.GetPartnerByIdUseCase;
import com.example.demo.infrastructure.services.CustomerService;
import com.example.demo.infrastructure.services.EventService;
import com.example.demo.infrastructure.services.PartnerService;

@Configuration
public class UseCaseConfig {

    private final CustomerService customerService;
    // private final CustomerRepository customerRepository;
    private final EventService eventService;
    private final PartnerService partnerService;

    public UseCaseConfig(
            final CustomerService customerService,
            // final CustomerRepository customerRepository,
            final EventService eventService,
            final PartnerService partnerService
    ) {
        this.customerService = Objects.requireNonNull(customerService);
        // this.customerRepository = Objects.requireNonNull(customerRepository);
        this.eventService = Objects.requireNonNull(eventService);
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        return new CreateCustomerUseCase(null);
    }

    @Bean
    public CreateEventUseCase createEventUseCase() {
        return new CreateEventUseCase(null, null);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(null);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new GetCustomerByIdUseCase(null);
    }

    @Bean
    public GetPartnerByIdUseCase getPartnerByIdUseCase() {
        return new GetPartnerByIdUseCase(null);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(null, null, null);
    }
}
