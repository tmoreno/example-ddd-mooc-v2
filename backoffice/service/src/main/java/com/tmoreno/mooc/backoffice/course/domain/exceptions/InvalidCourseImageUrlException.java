package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class InvalidCourseImageUrlException extends BaseDomainException {
    public InvalidCourseImageUrlException(String message) {
        super(
                "invalid-course-image-url",
                message
        );
    }
}
