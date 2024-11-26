package com.example.demo.application.usecases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.application.InMemoryEventRepository;
import com.example.demo.application.InMemoryPartnerRepository;
import com.example.demo.application.entities.Partner;
import com.example.demo.application.entities.PartnerId;
import com.example.demo.application.exceptions.ValidationException;

public class CreateEventUseCaseTest {

    @Test
    @DisplayName("should create a event")
    public void testCreate() {
        final var date = "2024-01-01";
        final var name = "Tony Amaral";
        final var totalSpots = 10;
        final var partner = Partner.newPartner("Tony Comp", "32.125.214/0001-55", "tony@teste3.com");
        final var partnerId = partner.getPartnerId().value();
        final var createInput = new CreateEventUseCase.Input(date, name, totalSpots, partnerId);

        final var eventRepository = new InMemoryEventRepository();
        final var partnerRepository = new InMemoryPartnerRepository();

        partnerRepository.create(partner);

        final var useCase = new CreateEventUseCase(partnerRepository, eventRepository);
        final var output = useCase.execute(createInput);

        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(date, output.date());
        Assertions.assertEquals(name, output.name());
        Assertions.assertEquals(totalSpots, output.totalSpots());
        Assertions.assertEquals(partnerId, output.partnerId());
    }

    @Test
    @DisplayName("should't create a event with no partner found")
    public void testCreateWithNoPartner() {
        final var date = "2024-01-01";
        final var name = "Tony Amaral";
        final var totalSpots = 10;
        final var partnerId = PartnerId.unique().value();
        final var createInput = new CreateEventUseCase.Input(date, name, totalSpots, partnerId);
        final var error = "Partner not found";

        final var eventRepository = new InMemoryEventRepository();
        final var partnerRepository = new InMemoryPartnerRepository();

        final var useCase = new CreateEventUseCase(partnerRepository, eventRepository);
        final var exception = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        Assertions.assertEquals(error, exception.getMessage());
    }
}
