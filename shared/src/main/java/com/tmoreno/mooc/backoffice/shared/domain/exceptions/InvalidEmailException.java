package com.tmoreno.mooc.backoffice.shared.domain.exceptions;

public final class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String invalidEmailValue) {
        super("Invalid email value: " + invalidEmailValue);
    }
}
