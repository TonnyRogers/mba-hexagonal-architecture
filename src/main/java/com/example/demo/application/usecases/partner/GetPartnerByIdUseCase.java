package com.example.demo.application.usecases.partner;

import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.domain.partner.PartnerId;
import com.example.demo.application.repositories.PartnerRepository;
import com.example.demo.application.usecases.UseCase;

public class GetPartnerByIdUseCase extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerRepository partnerRepository;

    public GetPartnerByIdUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return partnerRepository.partnerOfId(PartnerId.with(input.id))
                .map(p -> new Output(
                p.getPartnerId().value(),
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
