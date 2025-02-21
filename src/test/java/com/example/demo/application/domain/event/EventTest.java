package com.example.demo.application.domain.event;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.domain.customer.Customer;
import com.example.demo.application.domain.event.ticket.TicketStatus;
import com.example.demo.application.domain.partner.Partner;
import com.example.demo.application.exceptions.ValidationException;

public class EventTest {

    @Test
    @DisplayName("should create a event")
    public void testCreateEvent() {
        final var date = "2024-01-01";
        final var name = "Tony Amaral's Show";
        final var totalSpots = 10;
        final var partner = Partner.newPartner("Tony Comp", "32.125.214/0001-55", "tony@teste3.com");
        final var partnerId = partner.getPartnerId().value();
        final var expectedTickets = 0;

        final var event = Event.newEvent(name, date, totalSpots, partner);

        Assertions.assertNotNull(event.getEventId());
        Assertions.assertEquals(date, event.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertEquals(name, event.getName().value());
        Assertions.assertEquals(totalSpots, event.getTotalSpots());
        Assertions.assertEquals(partnerId, event.getPartnerId().value());
        Assertions.assertEquals(expectedTickets, event.getAllTickets().size());
    }

    @Test
    @DisplayName("should'n create a event with invalid name")
    public void testCreateEventWithInvalidName() {
        final var partner = Partner.newPartner("Tony Comp", "32.125.214/0001-55", "tony@teste3.com");
        final var expectedError = "Invalid name for Event";

        final var exception = Assertions.assertThrows(
                ValidationException.class,
                () -> Event.newEvent(null, "2021-01-01",
                        10, partner));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("should'n create a event with invalid date")
    public void testCreateEventWithInvalidDate() {
        final var partner = Partner.newPartner("Tony Comp", "32.125.214/0001-55", "tony@teste3.com");
        final var expectedError = "Invalid date for Event.";

        final var exception = Assertions.assertThrows(
                ValidationException.class,
                () -> Event.newEvent("Tony's talks", "20210101",
                        10, partner));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("should reserve an ticket event when is avaliable")
    public void testReserveEventTicket() {
        final var date = "2024-01-01";
        final var name = "Tony Amaral's Show";
        final var totalSpots = 10;
        final var partner = Partner.newPartner("Tony Comp", "32.125.214/0001-55", "tony@teste3.com");
        final var partnerId = partner.getPartnerId().value();
        final var expectedTickets = 1;
        final var expectedTicketOrder = 1;
        final var customerCPF = "265.359.485-55";
        final var customerEmail = "teste@test.com";
        final var customerName = "Tony Amaral";

        final var customer = Customer.newCustomer(customerName, customerCPF, customerEmail);
        final var event = Event.newEvent(name, date, totalSpots, partner);
        final var ticket = event.reserveTicket(customer.getCustomerId());

        Assertions.assertNotNull(ticket.getTicketId());
        Assertions.assertNotNull(ticket.getReservedAt());
        Assertions.assertNull(ticket.getPaidAt());
        Assertions.assertEquals(event.getEventId(), ticket.getEventId());
        Assertions.assertEquals(customer.getCustomerId(), ticket.getCustomerId());
        Assertions.assertEquals(TicketStatus.PENDING, ticket.getStatus());

        Assertions.assertNotNull(event.getEventId());
        Assertions.assertEquals(date, event.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertEquals(name, event.getName().value());
        Assertions.assertEquals(totalSpots, event.getTotalSpots());
        Assertions.assertEquals(partnerId, event.getPartnerId().value());
        Assertions.assertEquals(expectedTickets, event.getAllTickets().size());

        final var eventTicket = event.getAllTickets().iterator().next();
        Assertions.assertEquals(expectedTicketOrder, eventTicket.getOrdering());
        Assertions.assertEquals(event.getEventId(), eventTicket.getEventId());
        Assertions.assertEquals(customer.getCustomerId(), eventTicket.getCustomerId());
        Assertions.assertEquals(ticket.getTicketId(), eventTicket.getTicketId());
    }

    @Test
    @DisplayName("should'n reserve tickets to the same client")
    public void testReserveEventTicketTwice() {
        final var date = "2024-01-01";
        final var name = "Tony Amaral's Show";
        final var totalSpots = 1;
        final var partner = Partner.newPartner("Tony Comp", "32.125.214/0001-55", "tony@teste3.com");
        final var customerCPF = "265.359.485-55";
        final var customerEmail = "teste@test.com";
        final var customerName = "Tony Amaral";
        final var expectedError = "Email already registered";

        final var customer = Customer.newCustomer(customerName, customerCPF, customerEmail);
        final var event = Event.newEvent(name, date, totalSpots, partner);
        event.reserveTicket(customer.getCustomerId());

        final var exception = Assertions.assertThrows(
                ValidationException.class,
                () -> event.reserveTicket(customer.getCustomerId()));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("should'n reserve an ticket event when is sold out")
    public void testReserveUnavailableEventTicket() {
        final var date = "2024-01-01";
        final var name = "Tony Amaral's Show";
        final var totalSpots = 1;
        final var partner = Partner.newPartner("Tony Comp", "32.125.214/0001-55", "tony@teste3.com");
        final var customerCPF = "265.359.485-55";
        final var customerEmail = "teste@test.com";
        final var customerName = "Tony Amaral";
        final var expectedError = "Event sold out";

        final var customer = Customer.newCustomer(customerName, customerCPF, customerEmail);
        final var customer2 = Customer.newCustomer("Manel", "265.359.488-52", customerEmail);
        final var event = Event.newEvent(name, date, totalSpots, partner);
        event.reserveTicket(customer.getCustomerId());

        final var exception = Assertions.assertThrows(
                ValidationException.class,
                () -> event.reserveTicket(customer2.getCustomerId()));

        Assertions.assertEquals(expectedError, exception.getMessage());
    }
}
