package com.example.demo.application.usecases;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.InMemoryPartnerRepository;
import com.example.demo.application.entities.Partner;
import com.example.demo.application.entities.PartnerId;

public class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("should get partner by id")
    public void testGetById() {

        final var CNPJ = "26.535.948/0001-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var partnerRepository = new InMemoryPartnerRepository();

        final var createdPartner = Partner.newPartner(name, CNPJ, email);
        partnerRepository.create(createdPartner);
        final var partnerId = createdPartner.getPartnerId().value();

        final var input = new GetPartnerByIdUseCase.Input(createdPartner.getPartnerId().value());

        final var useCase = new GetPartnerByIdUseCase(partnerRepository);
        final var output = useCase.execute(input).get();
        // this get method above is only allowed to use in test scenario

        Assertions.assertEquals(partnerId, output.id());
        Assertions.assertEquals(CNPJ, output.cnpj());
        Assertions.assertEquals(email, output.email());
        Assertions.assertEquals(name, output.name());
    }

    @Test
    @DisplayName("should't get partner by id")
    public void testGetByIdFail() {

        final var id = UUID.randomUUID().toString();

        final var partnerRepository = new InMemoryPartnerRepository();
        final var input = new GetPartnerByIdUseCase.Input(id);
        partnerRepository.partnerOfId(PartnerId.with(id));

        final var useCase = new GetPartnerByIdUseCase(partnerRepository);
        final var output = useCase.execute(input);

        Assertions.assertTrue(output.isEmpty());
    }
}
