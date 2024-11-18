package com.example.demo.application.usecases;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.models.Partner;
import com.example.demo.services.PartnerService;

public class CreatePartnerUseCaseTest {

    @Test
    @DisplayName("should create a partner")
    public void testCreatePartner() {
        // given
        final var CNPJ = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";

        final var createInput = new CreatePartnerUseCase.Input(CNPJ, email, name);
        final var partnerService = Mockito.mock(PartnerService.class);

        // when
        Mockito.when(partnerService.findByCnpj(CNPJ)).thenReturn(Optional.empty());
        Mockito.when(partnerService.findByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(partnerService.save(Mockito.any())).thenAnswer(a -> {
            var partner = a.getArgument(0, Partner.class);
            partner.setId(UUID.randomUUID().getMostSignificantBits());
            return partner;
        });

        final var useCase = new CreatePartnerUseCase(partnerService);
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
        final var CNPJ = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(CNPJ, email, name);
        final var partnerService = Mockito.mock(PartnerService.class);

        final var createdPartner = new Partner();
        createdPartner.setCnpj(CNPJ);
        createdPartner.setName(name);
        createdPartner.setEmail(email);
        createdPartner.setId(UUID.randomUUID().getMostSignificantBits());

        // when
        Mockito.when(partnerService.findByCnpj(CNPJ)).thenReturn(Optional.of(createdPartner));

        final var useCase = new CreatePartnerUseCase(partnerService);
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }

    @Test
    @DisplayName("should't create a partner with duplicated email")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var CNPJ = "26535948555";
        final var email = "teste@test.com";
        final var name = "Tony Amaral";
        final var errorMessage = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(CNPJ, email, name);
        final var partnerService = Mockito.mock(PartnerService.class);

        final var createdPartner = new Partner();
        createdPartner.setCnpj(CNPJ);
        createdPartner.setName(name);
        createdPartner.setEmail(email);
        createdPartner.setId(UUID.randomUUID().getMostSignificantBits());

        // when
        Mockito.when(partnerService.findByEmail(email)).thenReturn(Optional.of(createdPartner));

        final var useCase = new CreatePartnerUseCase(partnerService);
        final var actualException = Assertions
                .assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }
}
