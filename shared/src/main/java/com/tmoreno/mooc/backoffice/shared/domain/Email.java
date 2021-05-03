package com.tmoreno.mooc.backoffice.shared.domain;

import com.tmoreno.mooc.backoffice.shared.domain.exceptions.InvalidEmailException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public final class Email {

    private final String value;

    public Email(String value) {
        if (EmailValidator.getInstance().isValid(value)) {
            this.value = value;
        }
        else {
            throw new InvalidEmailException(value);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
