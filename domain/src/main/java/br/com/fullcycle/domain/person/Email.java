package br.com.fullcycle.domain.person;

import br.com.fullcycle.domain.exceptions.ValidationException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")) {
            throw new ValidationException("Invalid value for Email");
        }
    }
}
