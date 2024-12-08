package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class InvalidCourseTitleException extends BaseDomainException {
    public InvalidCourseTitleException(String message) {
        super(
                "invalid-course-title",
                message
        );
    }
}
