package com.example.demo.controllers;

import java.net.URI;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.application.usecases.CreateEventUseCase;
import com.example.demo.application.usecases.SubscribeCustomerToEventUseCase;
import com.example.demo.dtos.EventDTO;
import com.example.demo.dtos.SubscribeDTO;
import com.example.demo.services.CustomerService;
import com.example.demo.services.EventService;
import com.example.demo.services.PartnerService;

@RestController
@RequestMapping(value = "events")
public class EventController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EventService eventService;

    @Autowired
    private PartnerService partnerService;

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<?> create(@RequestBody EventDTO dto) {
        try {
            final var partnerId = Objects.requireNonNull(dto.getPartner().getId(), "Partnet is required");
            final var useCase = new CreateEventUseCase(partnerService, eventService);
            final var output = useCase.execute(new CreateEventUseCase.Input(dto.getDate(), dto.getName(), dto.getTotalSpots(), partnerId));
            return ResponseEntity.created(URI.create("/events/" + output.id())).body(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }

    }

    @Transactional
    @PostMapping(value = "/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable Long id, @RequestBody SubscribeDTO dto) {
        try {
            final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
            final var output = useCase.execute(new SubscribeCustomerToEventUseCase.Input(id, dto.getCustomerId()));
            return ResponseEntity.ok(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }
}
