package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;

public final class CourseExistsException extends RuntimeException {
    public CourseExistsException(CourseId id, CourseTitle title) {
        super("A course with this id: " + id.getValue() + " or with this title: "+ title.getValue() + " already exists");
    }
}
