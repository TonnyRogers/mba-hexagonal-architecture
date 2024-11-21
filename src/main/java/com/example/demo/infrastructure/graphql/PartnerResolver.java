package com.example.demo.infrastructure.graphql;

import java.util.Objects;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.application.usecases.CreatePartnerUseCase;
import com.example.demo.application.usecases.GetPartnerByIdUseCase;
import com.example.demo.infrastructure.dtos.NewPartnerDTO;

@Controller
public class PartnerResolver {

    private final CreatePartnerUseCase createPartnerUseCase;
    private final GetPartnerByIdUseCase getPartnerByIdUseCase;

    public PartnerResolver(
            final CreatePartnerUseCase createPartnerUseCase,
            final GetPartnerByIdUseCase getPartnerByIdUseCase
    ) {
        this.createPartnerUseCase = Objects.requireNonNull(createPartnerUseCase);
        this.getPartnerByIdUseCase = Objects.requireNonNull(getPartnerByIdUseCase);
    }

    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument NewPartnerDTO input) {
        final var output = createPartnerUseCase.execute(new CreatePartnerUseCase.Input(input.cnpj(), input.email(), input.name()));

        return output;
    }

    @QueryMapping
    public GetPartnerByIdUseCase.Output partnerOfId(@Argument Long id) {
        return getPartnerByIdUseCase.execute(new GetPartnerByIdUseCase.Input(id))
                .orElse(null);
    }
}
