package br.com.fullcycle.hexagonal.application.domain.person;

import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CnpjTest {

    @Test
    @DisplayName("Deve instanciar um CNPJ")
    public void testCreateCNPJ() {

        // given
        final var expectedCNP = "45.536.538/0001-00";

        // when
        final var actualCnpj = new Cnpj(expectedCNP);

        // then
        Assertions.assertEquals(expectedCNP, actualCnpj.value());
    }

    @Test
    @DisplayName("Não deve instanciar um CNPJ inválido")
    public void testCreateCNPJWithInvalidValue() {

        // given
        final var expectedError = "Invalid value for CNPJ";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Cnpj("536.538/0001-00")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um CNPJ null")
    public void testCreateCNPJWithNullValue() {

        // given
        final var expectedError = "Invalid value for CNPJ";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Cnpj(null)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

}