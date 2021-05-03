package com.tmoreno.mooc.backoffice.courses.domain.exceptions;

public final class InvalidCourseDescriptionException extends RuntimeException {
    public InvalidCourseDescriptionException(String message) {
        super(message);
    }
}
