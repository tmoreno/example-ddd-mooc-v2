package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.domain.exceptions.InvalidDurationException;

import java.util.Objects;

public final class DurationInSeconds {

    private final int value;

    public DurationInSeconds(int value) {
        if (value > 0) {
            this.value = value;
        }
        else {
            throw new InvalidDurationException(value);
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DurationInSeconds{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DurationInSeconds duration = (DurationInSeconds) o;
        return value == duration.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
