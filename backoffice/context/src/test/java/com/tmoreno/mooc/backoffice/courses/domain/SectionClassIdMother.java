package com.tmoreno.mooc.backoffice.courses.domain;

import java.util.UUID;

public final class SectionClassIdMother {

    public static SectionClassId random() {
        return new SectionClassId(UUID.randomUUID().toString());
    }
}
