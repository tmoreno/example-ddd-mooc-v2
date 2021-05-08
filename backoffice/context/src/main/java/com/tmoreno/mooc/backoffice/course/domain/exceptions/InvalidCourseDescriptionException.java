package com.tmoreno.mooc.backoffice.course.domain.exceptions;

public final class InvalidCourseDescriptionException extends RuntimeException {
    public InvalidCourseDescriptionException(String message) {
        super(message);
    }
}
