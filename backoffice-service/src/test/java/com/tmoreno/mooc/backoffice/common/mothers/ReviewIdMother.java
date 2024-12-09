package com.tmoreno.mooc.backoffice.common.mothers;

import com.tmoreno.mooc.backoffice.review.domain.ReviewId;

import java.util.UUID;

public final class ReviewIdMother {

    public static ReviewId random() {
        return new ReviewId(UUID.randomUUID().toString());
    }
}
