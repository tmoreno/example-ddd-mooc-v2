package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class CourseTeacherNotFoundException extends BaseDomainException {
    public CourseTeacherNotFoundException(CourseId courseId, TeacherId teacherId) {
        super(
                "course-teacher-not-found",
                "Teacher: " + teacherId + " not found in course: " + courseId
        );
    }
}
