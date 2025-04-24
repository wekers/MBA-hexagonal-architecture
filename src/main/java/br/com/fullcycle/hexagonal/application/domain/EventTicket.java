package br.com.fullcycle.hexagonal.application.domain;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

public class EventTicket {

    private final TicketId ticketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private int ordering;

    protected EventTicket(final TicketId ticketId, final EventId eventId, final CustomerId customerId,  final Integer ordering) {
        if (ticketId == null || eventId == null || customerId == null) {
            throw new ValidationException("Invalid ticket, event id or customer id");
        }
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.setOrdering(ordering);
    }

    public TicketId ticketId() {
        return ticketId;
    }

    public EventId eventId() {
        return eventId;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public int ordering() {
        return ordering;
    }

    private void setOrdering(final Integer ordering) {
        if (ordering == null) {
            throw new ValidationException("Invalid ordering for event ticket");
        }
        this.ordering = ordering;
    }
}
