package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.entities.Event;
import br.com.fullcycle.hexagonal.application.entities.PartnerId;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;

import java.util.Objects;

public class CreateEventUseCase extends UseCase<CreateEventUseCase.Input, CreateEventUseCase.Output> {

    private final EventRepository eventRepository;
    private final PartnerRepository partnerRepository;

    public CreateEventUseCase(final EventRepository eventRepository, final PartnerRepository partnerRepository) {
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }


    @Override
    public Output execute(final Input input) {
        final var aPartner = partnerRepository.partnerOfId(PartnerId.with(input.partnerId)).orElseThrow( () ->  {
            return new ValidationException("Partner not found");
        });

        final var anEvent= eventRepository.create(Event.newEvent(input.name, input.date, input.totalSpots, aPartner));

        return new Output(
                anEvent.eventId().value(),
                input.date,
                anEvent.name().value(),
                anEvent.totalSpots(),
                anEvent.partnerId().value());
    }

    public record Input(String date, String name, String partnerId, Integer totalSpots) {}

    public record Output(String id, String date, String name, int totalSpots, String partnerId) {}
}
