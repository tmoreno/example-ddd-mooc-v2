package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class InvalidSectionClassTitleException extends BaseDomainException {
    public InvalidSectionClassTitleException(String message) {
        super(
                "invalid-section-class-title",
                message
        );
    }
}
