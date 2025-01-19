package com.tmoreno.mooc.backoffice.common.mothers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;

import java.util.UUID;

public final class CourseIdMother {

    public static CourseId random() {
        return new CourseId(UUID.randomUUID().toString());
    }
}
