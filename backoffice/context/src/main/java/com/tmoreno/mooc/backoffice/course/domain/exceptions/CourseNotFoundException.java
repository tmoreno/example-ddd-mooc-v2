package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;

public final class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(CourseId courseId) {
        super("Course not found: " + courseId.getValue());
    }
}
