package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.domain.exceptions.InvalidEmailException;
import org.apache.commons.validator.routines.EmailValidator;

public record Email(
    String value
) {
    public Email(String value) {
        if (EmailValidator.getInstance().isValid(value)) {
            this.value = value;
        } else {
            throw new InvalidEmailException(value);
        }
    }
}
