package com.example.demo.application.usecases.event;

import java.util.Objects;

import com.example.demo.application.domain.event.Event;
import com.example.demo.application.domain.partner.PartnerId;
import com.example.demo.application.exceptions.ValidationException;
import com.example.demo.application.repositories.EventRepository;
import com.example.demo.application.repositories.PartnerRepository;
import com.example.demo.application.usecases.UseCase;

public class CreateEventUseCase extends UseCase<CreateEventUseCase.Input, CreateEventUseCase.Output> {

    private final PartnerRepository partnerRepository;
    private final EventRepository eventRepository;

    public CreateEventUseCase(final PartnerRepository partnerRepository, final EventRepository eventRepository) {
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Output execute(final Input input) {

        final var partner = partnerRepository.partnerOfId(PartnerId.with(input.partnerId))
                .orElseThrow(() -> new ValidationException("Partner not found"));

        final var event = eventRepository.create(Event.newEvent(input.name, input.date, input.totalSpots, partner));

        return new Output(
                event.getEventId().value(),
                input.date,
                event.getName().value(),
                event.getTotalSpots(),
                event.getPartnerId().value()
        );
    }

    public record Input(String date, String name, Integer totalSpots, String partnerId) {

    }

    public record Output(String id, String date, String name, int totalSpots, String partnerId) {

    }
}
