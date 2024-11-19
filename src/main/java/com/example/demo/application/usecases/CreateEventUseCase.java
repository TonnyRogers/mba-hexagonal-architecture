package com.example.demo.application.usecases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.example.demo.application.UseCase;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.infrastructure.models.Event;
import com.example.demo.infrastructure.services.EventService;
import com.example.demo.infrastructure.services.PartnerService;

public class CreateEventUseCase extends UseCase<CreateEventUseCase.Input, CreateEventUseCase.Output> {

    private final PartnerService partnerService;
    private final EventService eventService;

    public CreateEventUseCase(final PartnerService partnerService, final EventService eventService) {
        this.eventService = Objects.requireNonNull(eventService);
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @Override
    public Output execute(final Input input) {
        var event = new Event();
        event.setDate(LocalDate.parse(input.date, DateTimeFormatter.ISO_DATE));
        event.setName(input.name);
        event.setTotalSpots(input.totalSpots);

        partnerService.findById(input.partnerId)
                .ifPresentOrElse(event::setPartner, () -> {
                    throw new ValidationException("Partner not found");
                });

        event = eventService.save(event);

        return new Output(event.getId(), input.date, event.getName(), input.totalSpots, input.partnerId);
    }

    public record Input(String date, String name, Integer totalSpots, Long partnerId) {

    }

    public record Output(Long id, String date, String name, int totalSpots, Long partnerId) {

    }
}
