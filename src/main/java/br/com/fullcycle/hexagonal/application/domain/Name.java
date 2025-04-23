package br.com.fullcycle.hexagonal.application.domain;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

public record Name(String value) {
    public Name {
        if (value == null || value.isBlank()) {
            throw new ValidationException("Invalid value for Name");
        }
    }
}
