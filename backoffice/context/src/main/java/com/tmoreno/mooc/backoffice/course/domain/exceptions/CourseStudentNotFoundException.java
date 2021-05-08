package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class CourseStudentNotFoundException extends RuntimeException {
    public CourseStudentNotFoundException(CourseId courseId, StudentId studentId) {
        super("Student: " + studentId + " not found in course: " + courseId);
    }
}
