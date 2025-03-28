package com.example.demo.infrastructure.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

import com.example.demo.application.usecases.customer.CreateCustomerUseCase;
import com.example.demo.infrastructure.dtos.NewCustomerDTO;
import com.example.demo.infrastructure.jpa.repositories.CustomerJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerJpaRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um cliente")
    public void testCreate() throws Exception {

        var customer = new NewCustomerDTO("12345678901", "john.doe@gmail.com", "John Doe");

        final var result = this.mvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, NewCustomerDTO.class);
        Assertions.assertEquals(customer.name(), actualResponse.name());
        Assertions.assertEquals(customer.cpf(), actualResponse.cpf());
        Assertions.assertEquals(customer.email(), actualResponse.email());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    public void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        var customer = new NewCustomerDTO("12345678901", "john.doe@gmail.com", "John Doe");

        // Cria o primeiro cliente
        this.mvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        customer = new NewCustomerDTO("12345678901", "john2@gmail.com", "John Doe");

        // Tenta criar o segundo cliente com o mesmo CPF
        this.mvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer))
        )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Customer already exists"));
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {

        var customer = new NewCustomerDTO("12345678901", "john.doe@gmail.com", "John Doe");

        // Cria o primeiro cliente
        this.mvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        customer = new NewCustomerDTO("99999918901", "john.doe@gmail.com", "John Doe");

        // Tenta criar o segundo cliente com o mesmo CPF
        this.mvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer))
        )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Customer already exists"));
    }

    @Test
    @DisplayName("Deve obter um cliente por id")
    public void testGet() throws Exception {

        var customer = new NewCustomerDTO("12345678901", "john.doe@gmail.com", "John Doe");

        final var createResult = this.mvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customer))
        )
                .andReturn().getResponse().getContentAsByteArray();

        var customerId = mapper.readValue(createResult, CreateCustomerUseCase.Output.class).id();

        final var result = this.mvc.perform(
                MockMvcRequestBuilders.get("/customers/{id}", customerId)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, CreateCustomerUseCase.Output.class);
        Assertions.assertEquals(customerId, actualResponse.id());
        Assertions.assertEquals(customer.name(), actualResponse.name());
        Assertions.assertEquals(customer.cpf(), actualResponse.cpf());
        Assertions.assertEquals(customer.email(), actualResponse.email());
    }
}
