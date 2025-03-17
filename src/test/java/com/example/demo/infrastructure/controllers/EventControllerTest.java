package com.example.demo.infrastructure.controllers;

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

import com.example.demo.application.usecases.event.CreateEventUseCase;
import com.example.demo.infrastructure.dtos.NewEventDTO;
import com.example.demo.infrastructure.dtos.SubscribeDTO;
import com.example.demo.infrastructure.jpa.entities.CustomerEntity;
import com.example.demo.infrastructure.jpa.entities.PartnerEntity;
import com.example.demo.infrastructure.jpa.repositories.CustomerJpaRepository;
import com.example.demo.infrastructure.jpa.repositories.EventJpaRepository;
import com.example.demo.infrastructure.jpa.repositories.PartnerJpaRepository;
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
    private CustomerJpaRepository customerRepository;

    @Autowired
    private PartnerJpaRepository partnerRepository;

    @Autowired
    private EventJpaRepository eventRepository;

    private CustomerEntity johnDoe;
    private PartnerEntity disney;

    @BeforeEach
    void setUp() {
        johnDoe = customerRepository.save(new CustomerEntity(null, "John Doe", "123", "john@gmail.com"));
        disney = partnerRepository.save(new PartnerEntity(null, "Disney", "456", "disney@gmail.com"));
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

        var event = new NewEventDTO("Disney on Ice", "2021-01-01", 100, disney.getId().toString());

        final var result = this.mvc.perform(
                MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(event))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, NewEventDTO.class);
        Assertions.assertEquals(event.date(), actualResponse.date());
        Assertions.assertEquals(event.totalSpots(), actualResponse.totalSpots());
        Assertions.assertEquals(event.name(), actualResponse.name());
    }

    @Test
    @Transactional
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() throws Exception {

        var event = new NewEventDTO("Disney on Ice", "2021-01-01", 100, disney.getId().toString());

        final var createResult = this.mvc.perform(
                MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(event))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var eventId = mapper.readValue(createResult, CreateEventUseCase.Output.class).id();

        var sub = new SubscribeDTO(johnDoe.getId().toString(), null);

        this.mvc.perform(
                MockMvcRequestBuilders.post("/events/{id}/subscribe", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sub))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualEvent = eventRepository.findById(Long.parseLong(eventId)).get();
        Assertions.assertEquals(1, actualEvent.getTickets().size());
    }
}
