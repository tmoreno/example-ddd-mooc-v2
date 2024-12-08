package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.List;

public final class FindReviewsQueryResponse extends QueryResponse {

    private final List<FindReviewQueryResponse> reviews;

    public FindReviewsQueryResponse(List<FindReviewQueryResponse> reviews) {
        this.reviews = reviews;
    }

    public List<FindReviewQueryResponse> getReviews() {
        return reviews;
    }
}
