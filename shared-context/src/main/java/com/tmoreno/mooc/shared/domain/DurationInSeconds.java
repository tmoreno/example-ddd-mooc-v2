package com.tmoreno.mooc.shared.domain;

import com.tmoreno.mooc.shared.domain.exceptions.InvalidDurationException;

public record DurationInSeconds(
    int value
) {
    public DurationInSeconds(int value) {
        if (value > 0) {
            this.value = value;
        } else {
            throw new InvalidDurationException(value);
        }
    }
}
