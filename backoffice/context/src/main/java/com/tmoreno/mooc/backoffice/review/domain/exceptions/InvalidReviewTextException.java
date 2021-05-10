package com.tmoreno.mooc.backoffice.review.domain.exceptions;

public final class InvalidReviewTextException extends RuntimeException {
    public InvalidReviewTextException(String message) {
        super(message);
    }
}
