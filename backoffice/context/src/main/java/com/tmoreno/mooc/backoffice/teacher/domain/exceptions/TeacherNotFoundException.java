package com.tmoreno.mooc.backoffice.teacher.domain.exceptions;

import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;

public final class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(TeacherId teacherId) {
        super("Teacher not found: " + teacherId.getValue());
    }
}
