package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class InvalidSectionTitleException extends BaseDomainException {
    public InvalidSectionTitleException(String message) {
        super(
                "invalid-section-title",
                message
        );
    }
}
