package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.shared.query.QueryParams;

public final class FindReviewQueryParams extends QueryParams {

    private String reviewId;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}
