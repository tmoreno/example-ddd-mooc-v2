package com.tmoreno.mooc.backoffice.courses.domain.exceptions;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;

public final class CourseTeacherNotFoundException extends RuntimeException {
    public CourseTeacherNotFoundException(CourseId courseId, TeacherId teacherId) {
        super("Teacher: " + teacherId + " not found in course: " + courseId);
    }
}
