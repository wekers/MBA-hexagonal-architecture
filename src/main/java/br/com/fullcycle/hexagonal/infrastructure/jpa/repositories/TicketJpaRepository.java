package br.com.fullcycle.hexagonal.infrastructure.jpa.repositories;

import br.com.fullcycle.hexagonal.infrastructure.jpa.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicketJpaRepository extends CrudRepository<TicketEntity, Long> {

    Optional<TicketEntity> finfByEventIdAndCustomerId(Long eventId, Long customerId);
}
