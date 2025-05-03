package br.com.fullcycle.domain.partner;

import br.com.fullcycle.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class PartnerTest {

    @Test
    @DisplayName("Deve instanciar um partner")
    public void testCreatePartner() {

        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";


        // when
        final var actualPartner = Partner.newPartner(expectedName, expectedCNPJ, expectedEmail);

        // then
        Assertions.assertNotNull(actualPartner.partnerId());
        Assertions.assertEquals(expectedCNPJ, actualPartner.cnpj().value());
        Assertions.assertEquals(expectedName, actualPartner.name().value());
        Assertions.assertEquals(expectedEmail, actualPartner.email().value());
    }

    @Test
    @DisplayName("Não deve instanciar um partner com CNPJ inválido")
    public void testCreatePartnerWithInvalidCPFShouldFail() {

        // given
        final var expectedError = "Invalid value for CNPJ";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Partner.newPartner("John Doe", "536.538/0001-00", "john.doe@gmail.com")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }


    @Test
    @DisplayName("Não deve instanciar um partner com nome inválido")
    public void testCreatePartnerWithInvalidNameShouldFail() {

        // given
        final var expectedError = "Invalid value for Name";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Partner.newPartner(null, "41.536.538/0001-00", "john.doe@gmail.com")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um Partner com email inválido")
    public void testCreatePartnerWithInvalidEmailShouldFail() {

        // given
        final var expectedError = "Invalid value for Email";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@g")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }


}

