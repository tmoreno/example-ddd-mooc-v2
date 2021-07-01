package com.tmoreno.mooc.shared.domain.exceptions;

public final class InvalidEmailException extends BaseDomainException {
    public InvalidEmailException(String invalidEmailValue) {
        super(
                "invalid-email",
                "Invalid email value: " + invalidEmailValue
        );
    }
}
