package com.tmoreno.mooc.backoffice.teachers.domain;

import java.util.UUID;

public final class TeacherIdMother {

    public static TeacherId random() {
        return new TeacherId(UUID.randomUUID().toString());
    }
}
