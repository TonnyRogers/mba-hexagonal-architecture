package com.example.demo.application.usecases;

import java.util.Objects;

import com.example.demo.application.UseCase;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.models.Partner;
import com.example.demo.infrastructure.services.PartnerService;

public class CreatePartnerUseCase extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerService partnerService;

    public CreatePartnerUseCase(final PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @Override
    public Output execute(final Input input) {
        if (partnerService.findByCnpj(input.cpnj).isPresent() || partnerService.findByEmail(input.email).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        var partner = new Partner();
        partner.setCnpj(input.cpnj);
        partner.setEmail(input.email);
        partner.setName(input.name);
        partner = partnerService.save(partner);

        return new Output(partner.getId(), partner.getCnpj(), partner.getEmail(), partner.getName());
    }

    public record Input(String cpnj, String email, String name) {

    }

    public record Output(Long id, String cnpj, String email, String name) {

    }
}
