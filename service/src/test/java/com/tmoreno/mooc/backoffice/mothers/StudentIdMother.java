package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.student.domain.StudentId;

import java.util.UUID;

public final class StudentIdMother {

    public static StudentId random() {
        return new StudentId(UUID.randomUUID().toString());
    }
}
