package com.tmoreno.mooc.backoffice.teacher.domain.exceptions;

import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.Email;

public final class TeacherExistsException extends RuntimeException {
    public TeacherExistsException(TeacherId teacherId, Email email) {
        super("A teacher with this id: " + teacherId.getValue() + " or with this email: "+ email.getValue() + " already exists");
    }
}
