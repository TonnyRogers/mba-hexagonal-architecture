package com.example.demo.application.usecases.partner;

import java.util.Objects;

import com.example.demo.application.domain.partner.Partner;
import com.example.demo.application.domain.person.Cnpj;
import com.example.demo.application.domain.person.Email;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.application.repositories.PartnerRepository;
import com.example.demo.application.usecases.UseCase;

public class CreatePartnerUseCase extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerRepository partnerRepository;

    public CreatePartnerUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Output execute(final Input input) {
        if (partnerRepository.partnerOfCNPJ(new Cnpj(input.cpnj)).isPresent() || partnerRepository.partnerOfEmail(new Email(input.email)).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        var partner = Partner.newPartner(input.name, input.cpnj, input.email);
        partner = partnerRepository.create(partner);

        return new Output(
                partner.getPartnerId().value(),
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
