package com.tmoreno.mooc.shared.domain.exceptions;

public final class InvalidIdentifierException extends RuntimeException {
    public InvalidIdentifierException(String invalidIdentifierValue) {
        super("Invalid identifier value: " + invalidIdentifierValue);
    }
}
