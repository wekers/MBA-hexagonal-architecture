package br.com.fullcycle.application.customer;

import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.customer.CustomerRepository;

import java.util.Objects;
import java.util.Optional;

public class GetCustomerByIdUseCase
        extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {

    private final CustomerRepository customerRepository;

    public GetCustomerByIdUseCase(final CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return customerRepository.customerOfId(CustomerId.with(input.id))
                .map(c -> new Output(
                        c.customerId().value(),
                        c.cpf().value(),
                        c.email().value(),
                        c.name().value())
                );
    }

    public record Input(String id) {
    }

    public record Output(String id, String cpf, String email, String name) {
    }
}
