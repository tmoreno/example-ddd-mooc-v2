package com.tmoreno.mooc.shared.domain.exceptions;

public final class InvalidDurationException extends BaseDomainException {
    public InvalidDurationException(long value) {
        super(
                "invalid-duration",
                "Duration value have to be more than 0: " + value
        );
    }
}
