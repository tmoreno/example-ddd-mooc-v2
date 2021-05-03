package com.tmoreno.mooc.backoffice.courses.domain.exceptions;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;

public final class CourseStudentNotFoundException extends RuntimeException {
    public CourseStudentNotFoundException(CourseId courseId, StudentId studentId) {
        super("Student: " + studentId + " not found in course: " + courseId);
    }
}
