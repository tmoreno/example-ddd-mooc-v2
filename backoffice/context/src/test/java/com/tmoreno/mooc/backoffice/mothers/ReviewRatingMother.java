package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.ReviewRating;
import org.apache.commons.lang3.RandomUtils;

public final class ReviewRatingMother {
    public static ReviewRating random() {
        return ReviewRating.values()[RandomUtils.nextInt(0, ReviewRating.values().length)];
    }
}
