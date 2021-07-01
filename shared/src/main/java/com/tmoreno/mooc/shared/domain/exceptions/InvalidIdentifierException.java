package com.tmoreno.mooc.shared.domain.exceptions;

public final class InvalidIdentifierException extends BaseDomainException {
    public InvalidIdentifierException(String invalidIdentifierValue) {
        super(
                "invalid-identifier",
                "Invalid identifier value: " + invalidIdentifierValue
        );
    }
}
