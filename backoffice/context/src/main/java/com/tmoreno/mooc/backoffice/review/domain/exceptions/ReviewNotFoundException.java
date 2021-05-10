package com.tmoreno.mooc.backoffice.review.domain.exceptions;

import com.tmoreno.mooc.backoffice.review.domain.ReviewId;

public final class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(ReviewId reviewId) {
        super("Review not found: " + reviewId);
    }
}
