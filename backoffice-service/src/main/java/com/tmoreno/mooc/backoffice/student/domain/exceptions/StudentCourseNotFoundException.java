package com.tmoreno.mooc.backoffice.student.domain.exceptions;

import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class StudentCourseNotFoundException extends BaseDomainException {
    public StudentCourseNotFoundException(StudentId studentId, CourseId courseId) {
        super(
                "student-course-not-found",
                "Course: " + courseId + " not found in student: " + studentId
        );
    }
}
