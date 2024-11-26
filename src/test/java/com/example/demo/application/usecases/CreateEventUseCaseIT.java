package com.example.demo.application.usecases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.IntegrationTest;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.models.Partner;
import com.example.demo.infrastructure.repositories.EventRepository;
import com.example.demo.infrastructure.repositories.PartnerRepository;

import io.hypersistence.tsid.TSID;

public class CreateEventUseCaseIT extends IntegrationTest {

    @Autowired
    private CreateEventUseCase useCase;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void tearDown() {
        eventRepository.deleteAll();
        partnerRepository.deleteAll();
    }

    @Test
    @DisplayName("should create a event")
    public void testCreate() {
        final var partner = createPartner("2321321321", "Jose", "jose@test.com");
        final var date = "2024-01-01";
        final var name = "Tony Amaral";
        final var totalSpots = 10;
        final var partnerId = partner.getId().toString();

        final var createInput = new CreateEventUseCase.Input(date, name, totalSpots, partnerId);

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
        final var partnerId = TSID.fast().toString();
        final var createInput = new CreateEventUseCase.Input(date, name, totalSpots, partnerId);
        final var error = "Partner not found";

        final var exception = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        Assertions.assertEquals(error, exception.getMessage());
    }

    private Partner createPartner(String CNPJ, String name, String email) {
        final var createdPartner = new Partner();
        createdPartner.setCnpj(CNPJ);
        createdPartner.setName(name);
        createdPartner.setEmail(email);
        return partnerRepository.save(createdPartner);
    }
}
