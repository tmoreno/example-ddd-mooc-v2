package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class ChangeCourseAttributeException extends BaseDomainException {
    public ChangeCourseAttributeException(String message) {
        super(
                "change-course-attribute",
                message
        );
    }
}
