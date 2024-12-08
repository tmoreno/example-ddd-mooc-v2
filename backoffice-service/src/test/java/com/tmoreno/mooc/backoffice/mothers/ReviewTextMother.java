package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.review.domain.ReviewText;
import org.apache.commons.lang3.RandomStringUtils;

public final class ReviewTextMother {
    public static ReviewText random() {
        return new ReviewText(RandomStringUtils.randomAlphabetic(100, 5000));
    }
}
