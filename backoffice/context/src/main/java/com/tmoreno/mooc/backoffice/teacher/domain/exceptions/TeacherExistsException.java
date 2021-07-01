package com.tmoreno.mooc.backoffice.teacher.domain.exceptions;

import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class TeacherExistsException extends BaseDomainException {
    public TeacherExistsException(TeacherId teacherId, Email email) {
        super(
                "teacher-exists",
                "A teacher with this id: " + teacherId.getValue() + " or with this email: "+ email.getValue() + " already exists"
        );
    }
}
