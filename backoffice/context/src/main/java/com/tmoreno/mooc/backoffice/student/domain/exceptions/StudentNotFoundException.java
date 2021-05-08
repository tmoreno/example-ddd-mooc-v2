package com.tmoreno.mooc.backoffice.student.domain.exceptions;

import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(StudentId studentId) {
        super("Student not found: " + studentId);
    }
}
