package com.example.demo.application.usecases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.InMemoryCustomerRepository;
import com.example.demo.application.InMemoryEventRepository;
import com.example.demo.application.InMemoryTicketRepository;
import com.example.demo.application.domain.Customer;
import com.example.demo.application.domain.Event;
import com.example.demo.application.domain.EventId;
import com.example.demo.application.domain.Partner;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.models.TicketStatus;

public class SubscribeCustomerToEventUseCaseTest {

    @Test
    @DisplayName("should buy an ticket from event")
    public void testBuyEventTicket() {

        final var expectedTicketSize = 1;
        final var CPF = "265.359.485-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var partner = Partner.newPartner("Jose Partner", "32.326.326/0001-59", "jose@partner.com.br");
        final var event = Event.newEvent("Standup Session", "2024-12-16", 40, partner);
        final var customer = Customer.newCustomer(name, CPF, email);
        final var customerId = customer.getCustomerId().value();
        final var eventId = event.getEventId().value();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(eventId, customerId);
        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        customerRepository.create(customer);
        eventRepository.create(event);

        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var output = useCase.execute(subscribeInput);

        Assertions.assertEquals(eventId, output.eventId());
        Assertions.assertNotNull(output.ticketId());
        Assertions.assertNotNull(output.reservationDate());
        Assertions.assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());

        final var actualEvent = eventRepository.eventOfId(event.getEventId());
        Assertions.assertEquals(expectedTicketSize, actualEvent.get().getAllTickets().size());
    }

    @Test
    @DisplayName("should't buy an ticket from not existent event")
    public void testBuyEventTicketNonExistent() {

        final var expectedError = "Event not found";
        final var CPF = "265.359.485-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var customer = Customer.newCustomer(name, CPF, email);
        final var customerId = customer.getCustomerId();
        final var eventId = EventId.unique().value();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(eventId, customerId.value());
        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        customerRepository.create(customer);

        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var exception = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("should't buy an ticket twice from same customer")
    public void testBuyEventTicketTwiceToCustomer() {

        final var expectedError = "Email already registered";
        final var CPF = "905.033.000-20";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var partner = Partner.newPartner("Jose Partner", "32.326.326/0001-59", "jose@partner.com.br");
        final var event = Event.newEvent("Standup Session", "2024-12-16", 40, partner);
        final var customer = Customer.newCustomer(name, CPF, email);
        final var customerId = customer.getCustomerId();
        final var eventId = event.getEventId();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(eventId.value(), customerId.value());
        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        final var ticket = event.reserveTicket(customer.getCustomerId());

        customerRepository.create(customer);
        eventRepository.create(event);
        ticketRepository.create(ticket);

        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var exception = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("should't buy an ticket over limit")
    public void testBuyEventTicketOverLimit() {

        final var expectedError = "Event sold out";
        final var CPF = "905.033.000-20";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var partner = Partner.newPartner("Jose Partner", "32.326.326/0001-59", "jose@partner.com.br");
        final var event = Event.newEvent("Standup Session", "2024-12-16", 1, partner);
        final var customer = Customer.newCustomer(name, CPF, email);
        final var customer2 = Customer.newCustomer(
                "Manuel Braga", "002.725.490-96", "manuel.braga@email.com"
        );
        final var customerId = customer.getCustomerId();
        final var eventId = event.getEventId();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(eventId.value(), customerId.value());
        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        final var ticket = event.reserveTicket(customer2.getCustomerId());

        customerRepository.create(customer);
        customerRepository.create(customer2);
        eventRepository.create(event);
        ticketRepository.create(ticket);

        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);

        final var exception = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }
}
