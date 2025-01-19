package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class CourseNotFoundException extends BaseDomainException {
    public CourseNotFoundException(CourseId courseId) {
        super(
                "course-not-found",
                "Course not found: " + courseId.getValue()
        );
    }
}
