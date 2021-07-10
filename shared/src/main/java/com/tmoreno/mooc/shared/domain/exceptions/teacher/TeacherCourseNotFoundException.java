package com.tmoreno.mooc.shared.domain.exceptions.teacher;

import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class TeacherCourseNotFoundException extends BaseDomainException {
    public TeacherCourseNotFoundException(TeacherId teacherId, CourseId courseId) {
        super(
                "teacher-course-not-found",
                "Course: " + courseId.getValue() + " not found in teacher: " + teacherId.getValue()
        );
    }
}
