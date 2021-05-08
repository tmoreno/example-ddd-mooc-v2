package com.tmoreno.mooc.backoffice.course.domain.exceptions;

public final class InvalidCourseTitleException extends RuntimeException {
    public InvalidCourseTitleException(String message) {
        super(message);
    }
}
