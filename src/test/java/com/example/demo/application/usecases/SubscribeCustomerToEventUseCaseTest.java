package com.example.demo.application.usecases;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.models.Customer;
import com.example.demo.infrastructure.models.Event;
import com.example.demo.infrastructure.models.Ticket;
import com.example.demo.infrastructure.models.TicketStatus;
import com.example.demo.infrastructure.services.CustomerService;
import com.example.demo.infrastructure.services.EventService;

import io.hypersistence.tsid.TSID;

public class SubscribeCustomerToEventUseCaseTest {

    @Test
    @DisplayName("should buy an ticket from event")
    public void testBuyEventTicket() {

        final var expectedTicketSize = 1;
        final var customerId = TSID.fast().toLong();
        final var eventId = TSID.fast().toLong();
        final var CPF = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var customer = new Customer();
        customer.setId(customerId);
        customer.setCpf(CPF);
        customer.setName(name);
        customer.setEmail(email);

        final var event = new Event();
        event.setId(eventId);
        event.setName("Disney");
        event.setTotalSpots(20);

        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);
        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(event.getId(), customer.getId());

        Mockito.when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(eventService.findById(eventId)).thenReturn(Optional.of(event));
        Mockito.when(eventService.findTicketByEventIdAndCustomerId(eventId, customerId))
                .thenReturn(Optional.empty());
        Mockito.when(eventService.save(Mockito.any())).thenAnswer(answer -> {
            final var eventItem = answer.getArgument(0, Event.class);
            Assertions.assertEquals(expectedTicketSize, eventItem.getTickets().size());
            return eventItem;
        });

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var output = useCase.execute(subscribeInput);

        Assertions.assertEquals(eventId, output.eventId());
        Assertions.assertNotNull(output.reservationDate());
        Assertions.assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());
    }

    @Test
    @DisplayName("should't buy an ticket from not existent event")
    public void testBuyEventTicketNonExistent() {

        final var expectedError = "Event not found";
        final var customerId = TSID.fast().toLong();
        final var eventId = TSID.fast().toLong();
        final var CPF = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var customer = new Customer();
        customer.setId(customerId);
        customer.setCpf(CPF);
        customer.setName(name);
        customer.setEmail(email);

        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);
        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(eventId, customerId);

        Mockito.when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(eventService.findById(eventId)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var exception = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("should't buy an ticket twice from same customer")
    public void testBuyEventTicketTwiceToCustomer() {

        final var expectedError = "Email already registered";
        final var customerId = TSID.fast().toLong();
        final var eventId = TSID.fast().toLong();
        final var CPF = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var customer = new Customer();
        customer.setId(customerId);
        customer.setCpf(CPF);
        customer.setName(name);
        customer.setEmail(email);

        final var event = new Event();
        event.setId(eventId);
        event.setName("Disney");
        event.setTotalSpots(20);

        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);
        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(event.getId(), customer.getId());

        Mockito.when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(eventService.findById(eventId)).thenReturn(Optional.of(event));
        Mockito.when(eventService.findTicketByEventIdAndCustomerId(eventId, customerId))
                .thenReturn(Optional.of(new Ticket()));

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var exception = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("should't buy an ticket over limit")
    public void testBuyEventTicketOverLimit() {

        final var expectedError = "Event sold out";
        final var customerId = TSID.fast().toLong();
        final var eventId = TSID.fast().toLong();
        final var CPF = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var customer = new Customer();
        customer.setId(customerId);
        customer.setCpf(CPF);
        customer.setName(name);
        customer.setEmail(email);

        final var event = new Event();
        event.setId(eventId);
        event.setName("Disney");
        event.setTotalSpots(0);

        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);
        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(event.getId(), customer.getId());

        Mockito.when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(eventService.findById(eventId)).thenReturn(Optional.of(event));
        Mockito.when(eventService.findTicketByEventIdAndCustomerId(eventId, customerId))
                .thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var exception = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }
}
