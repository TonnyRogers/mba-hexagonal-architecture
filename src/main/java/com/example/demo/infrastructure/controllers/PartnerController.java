package com.example.demo.infrastructure.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.application.usecases.CreatePartnerUseCase;
import com.example.demo.application.usecases.GetPartnerByIdUseCase;
import com.example.demo.infrastructure.dtos.PartnerDTO;
import com.example.demo.infrastructure.services.PartnerService;

@RestController
@RequestMapping(value = "partners")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PartnerDTO dto) {
        try {
            final var useCase = new CreatePartnerUseCase(partnerService);
            final var output = useCase.execute(new CreatePartnerUseCase.Input(dto.getCnpj(), dto.getEmail(), dto.getName()));

            return ResponseEntity.created(URI.create("/partners/" + output.id())).body(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        final var useCase = new GetPartnerByIdUseCase(partnerService);
        return useCase.execute(new GetPartnerByIdUseCase.Input(id))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

}
