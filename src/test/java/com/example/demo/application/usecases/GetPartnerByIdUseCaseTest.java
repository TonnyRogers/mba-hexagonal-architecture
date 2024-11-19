package com.example.demo.application.usecases;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.infrastructure.models.Partner;
import com.example.demo.infrastructure.services.PartnerService;

public class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("should get partner by id")
    public void testGetById() {

        final var id = UUID.randomUUID().getMostSignificantBits();
        final var CNPJ = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var partnerService = Mockito.mock(PartnerService.class);

        final var createdPartner = new Partner();
        createdPartner.setId(id);
        createdPartner.setCnpj(CNPJ);
        createdPartner.setName(name);
        createdPartner.setEmail(email);

        final var input = new GetPartnerByIdUseCase.Input(id);

        Mockito.when(partnerService.findById(id)).thenReturn(Optional.of(createdPartner));

        final var useCase = new GetPartnerByIdUseCase(partnerService);
        final var output = useCase.execute(input).get();
        // this get method above is only allowed to use in test scenario

        Assertions.assertEquals(id, output.id());
        Assertions.assertEquals(CNPJ, output.cnpj());
        Assertions.assertEquals(email, output.email());
        Assertions.assertEquals(name, output.name());
    }

    @Test
    @DisplayName("should't get partner by id")
    public void testGetByIdFail() {

        final var id = UUID.randomUUID().getMostSignificantBits();

        final var partnerService = Mockito.mock(PartnerService.class);
        final var input = new GetPartnerByIdUseCase.Input(id);

        Mockito.when(partnerService.findById(id)).thenReturn(Optional.empty());

        final var useCase = new GetPartnerByIdUseCase(partnerService);
        final var output = useCase.execute(input);

        Assertions.assertTrue(output.isEmpty());
    }
}
