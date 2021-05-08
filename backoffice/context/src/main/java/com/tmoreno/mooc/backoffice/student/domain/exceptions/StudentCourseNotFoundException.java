package com.tmoreno.mooc.backoffice.student.domain.exceptions;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentCourseNotFoundException extends RuntimeException {
    public StudentCourseNotFoundException(StudentId studentId, CourseId courseId) {
        super("Course: " + courseId + " not found in student: " + studentId);
    }
}
