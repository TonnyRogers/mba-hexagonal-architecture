package com.example.demo.application.usecases;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.models.Event;
import com.example.demo.models.Partner;
import com.example.demo.services.EventService;
import com.example.demo.services.PartnerService;

import io.hypersistence.tsid.TSID;

public class CreateEventUseCaseTest {

    @Test
    @DisplayName("should create a event")
    public void testCreate() {
        final var date = "2024-01-01";
        final var name = "Tony Amaral";
        final var totalSpots = 10;
        final var partnerId = TSID.fast().toLong();
        final var createInput = new CreateEventUseCase.Input(date, name, totalSpots, partnerId);

        final var eventService = Mockito.mock(EventService.class);
        final var partnerService = Mockito.mock(PartnerService.class);

        Mockito.when(partnerService.findById(Mockito.eq(partnerId)))
                .thenReturn(Optional.of(new Partner()));

        Mockito.when(eventService.save(Mockito.any())).thenAnswer(answer -> {
            final var event = answer.getArgument(0, Event.class);
            event.setId(TSID.fast().toLong());
            return event;
        });

        final var useCase = new CreateEventUseCase(partnerService, eventService);
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
        final var partnerId = TSID.fast().toLong();
        final var createInput = new CreateEventUseCase.Input(date, name, totalSpots, partnerId);
        final var error = "Partner not found";

        final var eventService = Mockito.mock(EventService.class);
        final var partnerService = Mockito.mock(PartnerService.class);

        Mockito.when(partnerService.findById(Mockito.eq(partnerId)))
                .thenReturn(Optional.empty());

        final var useCase = new CreateEventUseCase(partnerService, eventService);
        final var exception = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        Assertions.assertEquals(error, exception.getMessage());
    }
}
