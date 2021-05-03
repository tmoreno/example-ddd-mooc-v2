package com.tmoreno.mooc.backoffice.courses.domain.exceptions;

public final class InvalidCourseTitleException extends RuntimeException {
    public InvalidCourseTitleException(String message) {
        super(message);
    }
}
