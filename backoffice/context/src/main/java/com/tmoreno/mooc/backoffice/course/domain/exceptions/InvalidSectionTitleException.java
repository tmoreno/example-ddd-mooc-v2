package com.tmoreno.mooc.backoffice.course.domain.exceptions;

public final class InvalidSectionTitleException extends RuntimeException {
    public InvalidSectionTitleException(String message) {
        super(message);
    }
}
