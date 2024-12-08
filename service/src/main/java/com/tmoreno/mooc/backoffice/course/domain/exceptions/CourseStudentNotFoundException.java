package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class CourseStudentNotFoundException extends BaseDomainException {
    public CourseStudentNotFoundException(CourseId courseId, StudentId studentId) {
        super(
                "course-student-not-found",
                "Student: " + studentId + " not found in course: " + courseId
        );
    }
}
