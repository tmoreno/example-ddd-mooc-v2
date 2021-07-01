package com.tmoreno.mooc.backoffice.review.domain.exceptions;

import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class InvalidReviewTextException extends BaseDomainException {
    public InvalidReviewTextException(String message) {
        super(
                "invalid-review-text",
                message
        );
    }
}
