package com.example.demo.application.usecases;

import java.util.Objects;
import java.util.Optional;

import com.example.demo.application.UseCase;
import com.example.demo.infrastructure.services.PartnerService;

public class GetPartnerByIdUseCase extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerService partnerService;

    public GetPartnerByIdUseCase(final PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return partnerService.findById(input.id)
                .map(p -> new Output(p.getId(), p.getCnpj(), p.getEmail(), p.getName()));
    }

    public record Input(Long id) {

    }

    public record Output(Long id, String cnpj, String email, String name) {

    }
}
