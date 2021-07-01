package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class InvalidCourseDescriptionException extends BaseDomainException {
    public InvalidCourseDescriptionException(String message) {
        super(
                "invalid-course-description",
                message
        );
    }
}
