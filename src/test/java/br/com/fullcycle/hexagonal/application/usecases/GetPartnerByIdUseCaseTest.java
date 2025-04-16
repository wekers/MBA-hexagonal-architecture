package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.infrastructure.models.Partner;
import br.com.fullcycle.hexagonal.infrastructure.services.PartnerService;
import br.com.fullcycle.hexagonal.infrastructure.usecases.GetPartnerByIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("Deve obter um parceiro por id")
    public void testGetById() {
        // given
        final Long expectedID = UUID.randomUUID().getMostSignificantBits();
        final var expectedCNPJ = "41536538000100";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";


        final var aPartner = new Partner();
        aPartner.setId(expectedID);
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setName(expectedName);
        aPartner.setEmail(expectedEmail);

        final var input = new GetPartnerByIdUseCase.Input(expectedID);

        // when
        final var partnerService =  mock(PartnerService.class);
        when(partnerService.findById(expectedID)).thenReturn(Optional.of(aPartner));

        final var useCase = new GetPartnerByIdUseCase(partnerService);
        final var output = useCase.execute(input).get();

        // then
        Assertions.assertEquals(expectedID, output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedEmail, output.email());
    }

    @Test
    @DisplayName("Deve obter um vazio ao tentar recuperar um parceiro não existente por id")
    public void testGetByIdWithInvalidId() {
        // given
        final Long expectedID = UUID.randomUUID().getMostSignificantBits();

        final var input = new GetPartnerByIdUseCase.Input(expectedID);

        // when
        final var partnerService = mock(PartnerService.class);
        when(partnerService.findById(expectedID)).thenReturn(Optional.empty());

        final var useCase = new GetPartnerByIdUseCase(partnerService);
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());

    }

}