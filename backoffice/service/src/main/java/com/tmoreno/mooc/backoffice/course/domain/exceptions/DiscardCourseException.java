package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class DiscardCourseException extends BaseDomainException {
    public DiscardCourseException(String message) {
        super(
                "discard-course",
                message
        );
    }
}
