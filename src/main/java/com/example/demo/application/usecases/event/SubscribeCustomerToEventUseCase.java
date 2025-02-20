package com.example.demo.application.usecases.event;

import java.time.Instant;
import java.util.Objects;

import com.example.demo.application.domain.customer.CustomerId;
import com.example.demo.application.domain.event.EventId;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.application.repositories.CustomerRepository;
import com.example.demo.application.repositories.EventRepository;
import com.example.demo.application.repositories.TicketRepository;
import com.example.demo.application.usecases.UseCase;

public class SubscribeCustomerToEventUseCase extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {

    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    public SubscribeCustomerToEventUseCase(
            final CustomerRepository customerRepository,
            final EventRepository eventRepository,
            final TicketRepository ticketRepository
    ) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    @Override
    public Output execute(Input input) {
        var customer = customerRepository.customerOfId(CustomerId.with(input.customerId))
                .orElseThrow(() -> new ValidationException("Customer not found"));

        var event = eventRepository.eventOfId(EventId.with(input.eventId))
                .orElseThrow(() -> new ValidationException("Event not found"));

        var ticket = event.reserveTicket(customer.getCustomerId());
        ticketRepository.create(ticket);
        eventRepository.update(event);

        return new Output(
                event.getEventId().value(),
                ticket.getTicketId().value(),
                ticket.getStatus().name(),
                ticket.getReservedAt()
        );
    }

    public record Input(String eventId, String customerId) {

    }

    public record Output(String eventId, String ticketId, String ticketStatus, Instant reservationDate) {

    }

}
