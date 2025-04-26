package br.com.fullcycle.hexagonal.application.domain.event;

import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

public class EventTest {

    @Test
    @DisplayName("Deve criar um evento")
    public void testCreateEvent() throws Exception {

        // given
        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 100;
        final var expectedPartnerId = aPartner.partnerId().value();
        final var expectedTickets = 0;

        // when
        final var actualEvent = Event.newEvent(expectedName, expectedDate, expectedTotalSpots, aPartner);

        // then
        Assertions.assertNotNull(actualEvent.eventId());
        Assertions.assertEquals(expectedDate, actualEvent.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertEquals(expectedName, actualEvent.name().value());
        Assertions.assertEquals(expectedTotalSpots, actualEvent.totalSpots());
        Assertions.assertEquals(expectedPartnerId, actualEvent.partnerId().value());
        Assertions.assertEquals(expectedTickets, actualEvent.allTickets().size());

    }

    @Test
    @DisplayName("Não deve criar um evento com nome inválido")
    public void testCreateEventWithInvalidName() throws Exception {

        // given
        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var expectedError = "Invalid value for Name";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Event.newEvent(null, "2021-01-01", 10, aPartner)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());

    }

    @Test
    @DisplayName("Não deve criar um evento com data inválida")
    public void testCreateEventWithInvalidDate() throws Exception {

        // given
        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var expectedError = "Invalid date for Event";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Event.newEvent("Disney", "20210101", 10, aPartner)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());

    }


}
