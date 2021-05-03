package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.SectionId;

import java.util.UUID;

public final class SectionIdMother {
    public static SectionId random() {
        return new SectionId(UUID.randomUUID().toString());
    }
}
