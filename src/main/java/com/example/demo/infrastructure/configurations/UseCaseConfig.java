package com.example.demo.infrastructure.configurations;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.application.usecases.CreateCustomerUseCase;
import com.example.demo.application.usecases.CreateEventUseCase;
import com.example.demo.application.usecases.CreatePartnerUseCase;
import com.example.demo.application.usecases.GetCustomerByIdUseCase;
import com.example.demo.application.usecases.GetPartnerByIdUseCase;
import com.example.demo.application.usecases.SubscribeCustomerToEventUseCase;
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
        return new CreateEventUseCase(partnerService, eventService);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(partnerService);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new GetCustomerByIdUseCase(null);
    }

    @Bean
    public GetPartnerByIdUseCase getPartnerByIdUseCase() {
        return new GetPartnerByIdUseCase(partnerService);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(customerService, eventService);
    }
}
