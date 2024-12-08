package com.tmoreno.mooc.backoffice.review.domain.exceptions;

import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class ReviewNotFoundException extends BaseDomainException {
    public ReviewNotFoundException(ReviewId reviewId) {
        super(
                "review-not-found",
                "Review not found: " + reviewId.getValue()
        );
    }
}
