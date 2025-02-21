package com.example.demo.application.domain.event.ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.domain.customer.Customer;
import com.example.demo.application.domain.event.Event;
import com.example.demo.application.domain.partner.Partner;

public class TicketTest {

    @Test
    @DisplayName("should create an ticket")
    public void testCreateTicket() {
        final var date = "2024-01-01";
        final var name = "Tony Amaral's Show";
        final var totalSpots = 10;
        final var partner = Partner.newPartner("Tony Comp", "32.125.214/0001-55", "tony@teste3.com");
        final var customerCPF = "265.359.485-55";
        final var customerEmail = "teste@test.com";
        final var customerName = "Tony Amaral";

        final var customer = Customer.newCustomer(customerName, customerCPF, customerEmail);
        final var event = Event.newEvent(name, date, totalSpots, partner);
        final var ticket = Ticket.newTicket(customer.getCustomerId(), event.getEventId());

        Assertions.assertNotNull(ticket.getTicketId());
        Assertions.assertNotNull(ticket.getReservedAt());
        Assertions.assertNull(ticket.getPaidAt());
        Assertions.assertEquals(event.getEventId(), ticket.getEventId());
        Assertions.assertEquals(customer.getCustomerId(), ticket.getCustomerId());
        Assertions.assertEquals(TicketStatus.PENDING, ticket.getStatus());
    }
}
