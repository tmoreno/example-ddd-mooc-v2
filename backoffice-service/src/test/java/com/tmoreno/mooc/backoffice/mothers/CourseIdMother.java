package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.shared.domain.CourseId;

import java.util.UUID;

public final class CourseIdMother {

    public static CourseId random() {
        return new CourseId(UUID.randomUUID().toString());
    }
}
