package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.shared.query.Query;
import com.tmoreno.mooc.shared.query.VoidQueryParams;

import java.util.List;
import java.util.stream.Collectors;

public final class FindReviewsQuery implements Query<VoidQueryParams, FindReviewsQueryResponse> {

    private final ReviewRepository repository;

    public FindReviewsQuery(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public FindReviewsQueryResponse execute(VoidQueryParams params) {
        List<FindReviewQueryResponse> reviews = repository.findAll()
                .stream()
                .map(FindReviewQueryResponse::new)
                .collect(Collectors.toList());

        return new FindReviewsQueryResponse(reviews);
    }
}
