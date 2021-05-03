package com.tmoreno.mooc.backoffice.courses.domain;

import java.util.UUID;

public final class CourseIdMother {

    public static CourseId random() {
        return new CourseId(UUID.randomUUID().toString());
    }
}
