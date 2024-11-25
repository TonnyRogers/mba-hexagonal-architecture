package com.example.demo.application.usecases;

import java.util.Objects;

import com.example.demo.application.UseCase;
import com.example.demo.application.entities.Partner;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.application.repositories.PartnerRepository;

public class CreatePartnerUseCase extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerRepository partnerRepository;

    public CreatePartnerUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Output execute(final Input input) {
        if (partnerRepository.partnerOfCNPJ(input.cpnj).isPresent() || partnerRepository.partnerOfEmail(input.email).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        var partner = Partner.newPartner(input.name, input.cpnj, input.email);
        partner = partnerRepository.create(partner);

        return new Output(
                partner.getPartnerId().value().toString(),
                partner.getCnpj().value(),
                partner.getEmail().value(),
                partner.getName().value()
        );
    }

    public record Input(String cpnj, String email, String name) {

    }

    public record Output(String id, String cnpj, String email, String name) {

    }
}
