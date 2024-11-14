package com.example.demo.graphql;

import java.util.Objects;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.application.usecases.CreatePartnerUseCase;
import com.example.demo.application.usecases.GetPartnerByIdUseCase;
import com.example.demo.dtos.PartnerDTO;
import com.example.demo.services.PartnerService;

@Controller
public class PartnerResolver {

    private final PartnerService partnerService;

    public PartnerResolver(final PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument PartnerDTO input) {
        final var useCase = new CreatePartnerUseCase(partnerService);
        final var output = useCase.execute(new CreatePartnerUseCase.Input(input.getCnpj(), input.getEmail(), input.getName()));

        return output;
    }

    @QueryMapping
    public GetPartnerByIdUseCase.Output partnerOfId(@Argument Long id) {

        final var useCase = new GetPartnerByIdUseCase(partnerService);
        return useCase.execute(new GetPartnerByIdUseCase.Input(id))
                .orElse(null);
    }
}
