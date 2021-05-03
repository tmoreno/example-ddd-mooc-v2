package com.tmoreno.mooc.shared.domain;

import java.time.Instant;
import java.util.Objects;

public final class CreatedOn {

    private final Instant value;

    public CreatedOn(long value) {
        this.value = Instant.ofEpochMilli(value);
    }

    public long getValue() {
        return value.toEpochMilli();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatedOn createdOn = (CreatedOn) o;
        return Objects.equals(value, createdOn.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
