package com.tmoreno.mooc.backoffice.course.domain.exceptions;

public final class InvalidCourseImageUrlException extends RuntimeException {
    public InvalidCourseImageUrlException(String message) {
        super(message);
    }
}
