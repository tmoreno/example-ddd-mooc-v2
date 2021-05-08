package com.tmoreno.mooc.backoffice.course.domain.exceptions;

public final class InvalidCourseSummaryException extends RuntimeException {
    public InvalidCourseSummaryException(String message) {
        super(message);
    }
}
