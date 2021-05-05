package com.tmoreno.mooc.backoffice.teachers.domain.exceptions;

import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.Email;

public final class TeacherExistsException extends RuntimeException {
    public TeacherExistsException(TeacherId teacherId, Email email) {
        super("A teacher with this id: " + teacherId + " or with this email: "+ email + " already exists");
    }
}
