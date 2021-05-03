package com.tmoreno.mooc.backoffice.teachers.domain.exceptions;

import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;

public final class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(TeacherId teacherId) {
        super("Teacher not found: " + teacherId);
    }
}
