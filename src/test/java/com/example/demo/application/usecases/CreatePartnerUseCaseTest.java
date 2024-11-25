package com.example.demo.application.usecases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.InMemoryPartnerRepository;
import com.example.demo.application.entities.Partner;
import com.example.demo.application.exceptions.ValidationException;

public class CreatePartnerUseCaseTest {

    @Test
    @DisplayName("should create a partner")
    public void testCreatePartner() {
        // given
        final var CNPJ = "26.535.948/0001-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var createInput = new CreatePartnerUseCase.Input(CNPJ, email, name);
        final var partnerRepository = new InMemoryPartnerRepository();

        // when
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var output = useCase.execute(createInput);

        //then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(CNPJ, output.cnpj());
        Assertions.assertEquals(email, output.email());
        Assertions.assertEquals(name, output.name());
    }

    @Test
    @DisplayName("should't create a partner with duplicated CNPJ")
    public void testCreateWithDuplicatedCNPJShouldFail() {
        // given
        final var CNPJ = "26.535.948/0001-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(CNPJ, email, name);
        final var partnerRepository = new InMemoryPartnerRepository();
        final var createdPartner = Partner.newPartner(name, CNPJ, "teste3@test.com");
        partnerRepository.create(createdPartner);

        // when
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }

    @Test
    @DisplayName("should't create a partner with duplicated email")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var CNPJ = "26.535.948/0001-55";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(CNPJ, email, name);
        final var partnerRepository = new InMemoryPartnerRepository();

        final var createdPartner = Partner.newPartner(name, "22.152.684/0001-56", email);
        partnerRepository.create(createdPartner);

        // when
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }
}
