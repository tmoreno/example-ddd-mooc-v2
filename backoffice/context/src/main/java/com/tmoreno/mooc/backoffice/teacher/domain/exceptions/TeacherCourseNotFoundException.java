package com.tmoreno.mooc.backoffice.teacher.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;

public final class TeacherCourseNotFoundException extends RuntimeException {
    public TeacherCourseNotFoundException(TeacherId teacherId, CourseId courseId) {
        super("Course: " + courseId.getValue() + " not found in teacher: " + teacherId.getValue());
    }
}
