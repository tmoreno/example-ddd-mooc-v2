package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class InvalidCourseSummaryException extends BaseDomainException {
    public InvalidCourseSummaryException(String message) {
        super(
                "invalid-course-summary",
                message
        );
    }
}
