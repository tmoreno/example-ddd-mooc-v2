package com.tmoreno.mooc.backoffice.shared.domain.exceptions;

public final class InvalidPersonNameException extends RuntimeException {
    public InvalidPersonNameException(String invalidPersonName) {
        super("Invalid person name: " + invalidPersonName);
    }
}
