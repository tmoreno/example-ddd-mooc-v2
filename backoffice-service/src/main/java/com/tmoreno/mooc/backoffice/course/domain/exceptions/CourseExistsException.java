package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class CourseExistsException extends BaseDomainException {
    public CourseExistsException(CourseId id, CourseTitle title) {
        super(
                "course-exists",
                "A course with this id: " + id.getValue() + " or with this title: "+ title.value() + " already exists"
        );
    }
}
