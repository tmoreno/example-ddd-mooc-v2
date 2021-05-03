package com.tmoreno.mooc.backoffice.shared.domain.exceptions;

public final class InvalidDurationException extends RuntimeException {
    public InvalidDurationException(long value) {
        super("Duration value have to be more than 0: " + value);
    }
}
