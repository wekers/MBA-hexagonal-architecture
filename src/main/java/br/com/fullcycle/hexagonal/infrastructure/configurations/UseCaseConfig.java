package br.com.fullcycle.hexagonal.infrastructure.configurations;


import br.com.fullcycle.hexagonal.infrastructure.usecases.CreateCustomerUseCase;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final CustomerService customerService;

    public UseCaseConfig(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        return new CreateCustomerUseCase(customerService);
    }
}
