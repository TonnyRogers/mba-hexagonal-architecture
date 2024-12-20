package com.example.demo.application.usecases;

import java.time.Instant;
import java.util.Objects;

import com.example.demo.application.UseCase;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.models.Ticket;
import com.example.demo.infrastructure.models.TicketStatus;
import com.example.demo.infrastructure.services.CustomerService;
import com.example.demo.infrastructure.services.EventService;

public class SubscribeCustomerToEventUseCase extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {

    private final CustomerService customerService;
    private final EventService eventService;

    public SubscribeCustomerToEventUseCase(final CustomerService customerService, final EventService eventService) {
        this.customerService = Objects.requireNonNull(customerService);
        this.eventService = Objects.requireNonNull(eventService);
    }

    @Override
    public Output execute(Input input) {
        var customer = customerService.findById(input.customerId)
                .orElseThrow(() -> new ValidationException("Customer not found"));

        var event = eventService.findById(input.eventId)
                .orElseThrow(() -> new ValidationException("Event not found"));

        eventService.findTicketByEventIdAndCustomerId(input.eventId, input.customerId)
                .ifPresent(t -> {
                    throw new ValidationException("Email already registered");
                });

        if (event.getTotalSpots() < event.getTickets().size() + 1) {
            throw new ValidationException("Event sold out");
        }

        var ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setCustomer(customer);
        ticket.setReservedAt(Instant.now());
        ticket.setStatus(TicketStatus.PENDING);

        event.getTickets().add(ticket);
        eventService.save(event);

        return new Output(event.getId(), ticket.getStatus().name(), ticket.getReservedAt());
    }

    public record Input(Long eventId, Long customerId) {

    }

    public record Output(Long eventId, String ticketStatus, Instant reservationDate) {

    }

}
