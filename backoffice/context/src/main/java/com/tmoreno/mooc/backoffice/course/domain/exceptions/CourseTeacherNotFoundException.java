package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;

public final class CourseTeacherNotFoundException extends RuntimeException {
    public CourseTeacherNotFoundException(CourseId courseId, TeacherId teacherId) {
        super("Teacher: " + teacherId + " not found in course: " + courseId);
    }
}
