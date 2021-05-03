package com.tmoreno.mooc.backoffice.courses.domain;

import java.util.UUID;

public final class ReviewIdMother {

    public static ReviewId random() {
        return new ReviewId(UUID.randomUUID().toString());
    }
}
