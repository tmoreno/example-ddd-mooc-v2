package com.tmoreno.mooc.backoffice.review.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.InvalidReviewTextException;
import com.tmoreno.mooc.shared.domain.StringValueObject;
import org.apache.commons.lang3.StringUtils;

public final class ReviewText extends StringValueObject {

    private static final int MAX_LENGTH = 5000;
    private static final int MIN_LENGTH = 100;

    public ReviewText(String value) {
        super(value);

        ensureValidText();
    }

    private void ensureValidText() {
        if (StringUtils.isBlank(value)) {
            throw new InvalidReviewTextException("Review text can't be blank");
        }
        else if (value.length() > MAX_LENGTH) {
            throw new InvalidReviewTextException("Review text length is more than " + MAX_LENGTH);
        }
        else if (value.length() < MIN_LENGTH) {
            throw new InvalidReviewTextException("Review text length is less than " + MIN_LENGTH);
        }
    }
}
