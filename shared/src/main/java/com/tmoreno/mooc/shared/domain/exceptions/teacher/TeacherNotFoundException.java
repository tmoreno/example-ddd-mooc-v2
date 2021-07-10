package com.tmoreno.mooc.shared.domain.exceptions.teacher;

import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class TeacherNotFoundException extends BaseDomainException {
    public TeacherNotFoundException(TeacherId teacherId) {
        super(
                "teacher-not-found",
                "Teacher not found: " + teacherId.getValue()
        );
    }
}
