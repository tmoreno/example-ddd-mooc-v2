package com.tmoreno.mooc.backoffice.students.domain.exceptions;

import com.tmoreno.mooc.backoffice.students.domain.StudentId;

public final class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(StudentId studentId) {
        super("Student not found: " + studentId);
    }
}
