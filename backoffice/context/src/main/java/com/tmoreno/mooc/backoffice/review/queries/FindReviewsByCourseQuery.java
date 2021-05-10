package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.shared.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public final class FindReviewsByCourseQuery implements Query<FindReviewsByCourseQueryParams, FindReviewsQueryResponse> {

    private final ReviewRepository repository;

    public FindReviewsByCourseQuery(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public FindReviewsQueryResponse execute(FindReviewsByCourseQueryParams params) {
        CourseId courseId = new CourseId(params.getCourseId());

        List<FindReviewQueryResponse> reviews = repository.findByCourse(courseId)
                .stream()
                .map(FindReviewQueryResponse::new)
                .collect(Collectors.toList());

        return new FindReviewsQueryResponse(reviews);
    }
}
