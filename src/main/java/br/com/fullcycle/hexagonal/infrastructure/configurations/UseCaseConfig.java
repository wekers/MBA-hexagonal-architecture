package br.com.fullcycle.hexagonal.infrastructure.configurations;


import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import br.com.fullcycle.hexagonal.application.repositories.TicketRepository;
import br.com.fullcycle.hexagonal.application.usecases.customer.CreateCustomerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.customer.GetCustomerByIdUseCase;
import br.com.fullcycle.hexagonal.application.usecases.event.CreateEventUseCase;
import br.com.fullcycle.hexagonal.application.usecases.event.SubscribeCustomerToEventUseCase;
import br.com.fullcycle.hexagonal.application.usecases.partner.CreatePartnerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.partner.GetPartnerByIdUseCase;
import br.com.fullcycle.hexagonal.infrastructure.jpa.repositories.CustomerJpaRepository;
import br.com.fullcycle.hexagonal.infrastructure.jpa.repositories.EventJpaRepository;
import br.com.fullcycle.hexagonal.infrastructure.jpa.repositories.PartnerJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final PartnerRepository partnerRepository;
    private final TicketRepository  ticketRepository;

    public UseCaseConfig(CustomerRepository customerRepository, EventRepository eventRepository, PartnerRepository partnerRepository, TicketRepository ticketRepository) {
        this.customerRepository = customerRepository;
        this.eventRepository = eventRepository;
        this.partnerRepository = partnerRepository;
        this.ticketRepository = ticketRepository;
    }


    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        // TODO: Implementar, Fix dependency
        return new CreateCustomerUseCase(null);
    }

    @Bean
    public CreateEventUseCase createEventUseCase(EventJpaRepository eventJpaRepository, PartnerJpaRepository partnerJpaRepository) {
        // TODO: Implementar, Fix dependency
        return new CreateEventUseCase(null, null);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        // TODO: Implementar, Fix dependency
        return new CreatePartnerUseCase(null);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        // TODO: Implementar, Fix dependency
        return new GetCustomerByIdUseCase(null);
    }

    @Bean
    public GetPartnerByIdUseCase getPartnerByIdUseCase() {
        // TODO: Implementar, Fix dependency
        return new GetPartnerByIdUseCase(null);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase(CustomerJpaRepository customerRepository, EventJpaRepository eventRepository) {
        // TODO: Implementar, Fix dependency
        return new SubscribeCustomerToEventUseCase(null, null, null);
    }

}
