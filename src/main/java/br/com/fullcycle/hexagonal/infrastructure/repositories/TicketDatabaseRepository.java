package br.com.fullcycle.hexagonal.infrastructure.repositories;

import br.com.fullcycle.hexagonal.application.domain.event.ticket.Ticket;
import br.com.fullcycle.hexagonal.application.domain.event.ticket.TicketId;
import br.com.fullcycle.hexagonal.application.repositories.TicketRepository;
import br.com.fullcycle.hexagonal.infrastructure.jpa.entities.TicketEntity;
import br.com.fullcycle.hexagonal.infrastructure.jpa.repositories.TicketJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter
@Component
public class TicketDatabaseRepository implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;

    public TicketDatabaseRepository(TicketJpaRepository ticketJpaRepository) {
        this.ticketJpaRepository = Objects.requireNonNull(ticketJpaRepository);
    }


    @Override
    public Optional<Ticket> ticketOfId(final TicketId anId) {
        Objects.requireNonNull(anId, "Id must not be null");
        return this.ticketJpaRepository.findById(UUID.fromString(anId.value()))
        .map(TicketEntity::toTicket);
    }


    @Override
    @Transactional
    public Ticket create(Ticket ticket) {
        return this.ticketJpaRepository.save(TicketEntity.of(ticket)).toTicket();
    }

    @Override
    @Transactional
    public Ticket update(Ticket ticket) {
        return this.ticketJpaRepository.save(TicketEntity.of(ticket)).toTicket();
    }
}
