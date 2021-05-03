package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.ReviewId;

import java.util.UUID;

public final class ReviewIdMother {

    public static ReviewId random() {
        return new ReviewId(UUID.randomUUID().toString());
    }
}
