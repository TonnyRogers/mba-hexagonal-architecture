package com.example.demo.application.usecases;

import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.UseCase;
import com.example.demo.application.entities.PartnerId;
import com.example.demo.application.repositories.PartnerRepository;

public class GetPartnerByIdUseCase extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerRepository partnerRepository;

    public GetPartnerByIdUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return partnerRepository.partnerOfId(PartnerId.with(input.id))
                .map(p -> new Output(
                p.getPartnerId().value().toString(),
                p.getCnpj().value(),
                p.getEmail().value(),
                p.getName().value()
        ));

    }

    public record Input(String id) {

    }

    public record Output(String id, String cnpj, String email, String name) {

    }
}
