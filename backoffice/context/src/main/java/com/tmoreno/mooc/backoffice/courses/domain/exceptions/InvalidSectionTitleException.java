package com.tmoreno.mooc.backoffice.courses.domain.exceptions;

public final class InvalidSectionTitleException extends RuntimeException {
    public InvalidSectionTitleException(String message) {
        super(message);
    }
}
