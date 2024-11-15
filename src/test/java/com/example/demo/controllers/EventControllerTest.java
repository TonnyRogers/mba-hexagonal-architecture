package com.example.demo.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.EventDTO;
import com.example.demo.dtos.PartnerDTO;
import com.example.demo.dtos.SubscribeDTO;
import com.example.demo.models.Customer;
import com.example.demo.models.Partner;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.EventRepository;
import com.example.demo.repositories.PartnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private EventRepository eventRepository;

    private Customer johnDoe;
    private Partner disney;

    @BeforeEach
    void setUp() {
        johnDoe = customerRepository.save(new Customer(null, "John Doe", "123", "john@gmail.com"));
        disney = partnerRepository.save(new Partner(null, "Disney", "456", "disney@gmail.com"));
    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
        customerRepository.deleteAll();
        partnerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um evento")
    public void testCreate() throws Exception {

        var event = new EventDTO();
        event.setDate("2021-01-01");
        event.setName("Disney on Ice");
        event.setTotalSpots(100);
        event.setPartner(new PartnerDTO(disney.getId()));

        final var result = this.mvc.perform(
                MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(event))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, EventDTO.class);
        Assertions.assertEquals(event.getDate(), actualResponse.getDate());
        Assertions.assertEquals(event.getTotalSpots(), actualResponse.getTotalSpots());
        Assertions.assertEquals(event.getName(), actualResponse.getName());
    }

    @Test
    @Transactional
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() throws Exception {

        var event = new EventDTO();
        event.setDate("2021-01-01");
        event.setName("Disney on Ice");
        event.setTotalSpots(100);
        event.setPartner(new PartnerDTO(disney.getId()));

        final var createResult = this.mvc.perform(
                MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(event))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var eventId = mapper.readValue(createResult, EventDTO.class).getId();

        var sub = new SubscribeDTO();
        sub.setCustomerId(johnDoe.getId());

        this.mvc.perform(
                MockMvcRequestBuilders.post("/events/{id}/subscribe", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sub))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualEvent = eventRepository.findById(eventId).get();
        Assertions.assertEquals(1, actualEvent.getTickets().size());
    }
}
