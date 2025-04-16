package br.com.fullcycle.hexagonal.infrastructure.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.infrastructure.services.PartnerService;

import java.util.Optional;

public class GetPartnerByIdUseCase extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerService partnerServic;

    public GetPartnerByIdUseCase(PartnerService partnerServic) {
        this.partnerServic = partnerServic;
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return partnerServic.findById(input.id)
                .map(p -> new Output(p.getId(), p.getCnpj(), p.getEmail(), p.getName()));
    }

    public record Input(Long id) {
    }

    public record Output(Long id, String cnpj, String email, String name) {
    }
}
