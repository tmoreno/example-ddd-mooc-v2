package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class PublishCourseException extends BaseDomainException {
    public PublishCourseException(String message) {
        super(
                "publish-course",
                message
        );
    }
}
