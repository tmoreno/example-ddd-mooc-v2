package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.backoffice.review.domain.exceptions.ReviewNotFoundException;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.query.Query;

public final class FindReviewQuery implements Query<FindReviewQueryParams, FindReviewQueryResponse> {

    private final ReviewRepository repository;

    public FindReviewQuery(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public FindReviewQueryResponse execute(FindReviewQueryParams params) {
        ReviewId id = new ReviewId(params.getReviewId());

        Review review = repository.find(id).orElseThrow(() -> new ReviewNotFoundException(id));

        return new FindReviewQueryResponse(review);
    }
}
