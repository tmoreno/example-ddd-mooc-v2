package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;

import java.util.UUID;

public final class SectionClassIdMother {

    public static SectionClassId random() {
        return new SectionClassId(UUID.randomUUID().toString());
    }
}
