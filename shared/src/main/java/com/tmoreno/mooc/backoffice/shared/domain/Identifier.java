package com.tmoreno.mooc.backoffice.shared.domain;

import com.tmoreno.mooc.backoffice.shared.domain.exceptions.InvalidIdentifierException;

import java.util.Objects;
import java.util.UUID;

public class Identifier {
    private final UUID value;

    public Identifier() {
        value = UUID.randomUUID();
    }

    public Identifier(String value) {
        try {
            this.value = UUID.fromString(value);
        } catch (Throwable e) {
            throw new InvalidIdentifierException(value);
        }
    }

    public String getValue() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
