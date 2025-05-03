package br.com.fullcycle.application.event;

import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.ticket.TicketStatus;
import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.application.repository.InMemoryCustomerRepository;
import br.com.fullcycle.application.repository.InMemoryEventRepository;
import br.com.fullcycle.application.repository.InMemoryTicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubscribeCustomerToEventUseCaseTest {

    @Test
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() throws Exception {

        // given
        final var expectedTicketSize = 1;

        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = Event.newEvent("Disney",  "2021-01-01", 10, aPartner);
        final var aCustomer = Customer.newCustomer("John Gabriel", "123.456.789-01",  "john.doe@gmail.com");

        final var customerID = aCustomer.customerId().value();
        final var eventID = anEvent.eventId().value();


        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        customerRepository.create(aCustomer);
        eventRepository.create(anEvent);
        // when


        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var output = useCase.execute(subscribeInput);

        // then
        Assertions.assertEquals(eventID, output.eventId());
        Assertions.assertNotNull(output.ticketId());
        Assertions.assertNotNull(output.reservationDate());
        Assertions.assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());

        final var actualEvent = eventRepository.eventOfId(anEvent.eventId());
        Assertions.assertEquals(expectedTicketSize, actualEvent.get().allTickets().size());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento que não existe")
    public void testReserveTicketWithoutEvent() throws Exception {

        // given
        final var expectedError = "Event not found";

        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = Event.newEvent("Disney",  "2021-01-01", 10, aPartner);
        final var aCustomer = Customer.newCustomer("John Gabriel", "123.456.789-01",  "john.doe@gmail.com");

        final var customerID = aCustomer.customerId().value();
        final var eventID = anEvent.eventId().value();


        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        customerRepository.create(aCustomer);
        // when


        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ticket com um cliente não existe")
    public void testReserveTicketWithoutCustomer() throws Exception {

        // given
        final var expectedError = "Customer not found";

        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = Event.newEvent("Disney",  "2021-01-01", 10, aPartner);


        final var customerID = CustomerId.unique().value();
        final var eventID = anEvent.eventId().value();


        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        eventRepository.create(anEvent);

        // when

        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));
        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um mesmo cliente não pode comprar mais de um ticket para o mesmo evento")
    public void testReserveTicketMoreThanOnce() throws Exception {

        // given
        final var expectedError = "Customer already has a ticket for this event";

        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = Event.newEvent("Disney",  "2021-01-01", 10, aPartner);
        final var aCustomer = Customer.newCustomer("John Gabriel", "123.456.789-01",  "john.doe@gmail.com");

        final var customerID = aCustomer.customerId().value();
        final var eventID = anEvent.eventId().value();


        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        final var ticket = anEvent.reserveTicket(aCustomer.customerId());

        customerRepository.create(aCustomer);
        eventRepository.create(anEvent);
        ticketRepository.create(ticket);
        // when


        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um mesmo cliente não pode comprar de um evento que está esgotado")
    public void testReserveTicketWithoutSlots() throws Exception {

        // given
        final var expectedError = "Customer already has a ticket for this event";

        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = Event.newEvent("Disney",  "2021-01-01", 1, aPartner);
        final var aCustomer = Customer.newCustomer("John Gabriel", "123.456.789-01",  "john.doe@gmail.com");
        final var aCustomer2 = Customer.newCustomer("Pedro Doe", "123.111.789-01",  "pedro.doe@gmail.com");

        final var customerID = aCustomer.customerId().value();
        final var eventID = anEvent.eventId().value();


        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var ticketRepository = new InMemoryTicketRepository();

        final var ticket = anEvent.reserveTicket(aCustomer.customerId());

        customerRepository.create(aCustomer);
        customerRepository.create(aCustomer2);
        eventRepository.create(anEvent);
        ticketRepository.create(ticket);
        // when


        final var useCase = new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

}