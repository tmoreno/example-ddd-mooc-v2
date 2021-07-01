package com.tmoreno.mooc.shared.domain.exceptions;

public final class InvalidPersonNameException extends BaseDomainException {
    public InvalidPersonNameException(String invalidPersonName) {
        super(
                "invalid-person-name",
                "Invalid person name: " + invalidPersonName
        );
    }
}
