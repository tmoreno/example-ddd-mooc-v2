package com.tmoreno.mooc.backoffice.student.domain.exceptions;

import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class StudentNotFoundException extends BaseDomainException {
    public StudentNotFoundException(StudentId studentId) {
        super(
                "student-not-found",
                "Student not found: " + studentId.getValue()
        );
    }
}
