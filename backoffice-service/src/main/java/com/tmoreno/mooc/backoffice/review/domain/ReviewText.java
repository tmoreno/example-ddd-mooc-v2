package com.tmoreno.mooc.backoffice.review.domain;

import com.tmoreno.mooc.backoffice.review.domain.exceptions.InvalidReviewTextException;
import org.apache.commons.lang3.StringUtils;

public record ReviewText(
    String value
) {

    private static final int MAX_LENGTH = 5000;
    private static final int MIN_LENGTH = 100;

    public ReviewText {
        ensureValidText(value);
    }

    private void ensureValidText(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidReviewTextException("Review text can't be blank");
        } else if (value.length() > MAX_LENGTH) {
            throw new InvalidReviewTextException("Review text length is more than " + MAX_LENGTH);
        } else if (value.length() < MIN_LENGTH) {
            throw new InvalidReviewTextException("Review text length is less than " + MIN_LENGTH);
        }
    }
}
