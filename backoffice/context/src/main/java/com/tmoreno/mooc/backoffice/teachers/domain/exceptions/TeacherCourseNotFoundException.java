package com.tmoreno.mooc.backoffice.teachers.domain.exceptions;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;

public final class TeacherCourseNotFoundException extends RuntimeException {
    public TeacherCourseNotFoundException(TeacherId teacherId, CourseId courseId) {
        super("Course: " + courseId + " not found in teacher: " + teacherId);
    }
}
