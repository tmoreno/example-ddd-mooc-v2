package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.shared.query.Query;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class FindReviewsQuery implements Query<FindReviewsQueryParams, FindReviewsQueryResponse> {

    private final ReviewRepository repository;

    public FindReviewsQuery(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public FindReviewsQueryResponse execute(FindReviewsQueryParams params) {
        String courseId = params.getCourseId();
        String studentId = params.getStudentId();

        List<FindReviewQueryResponse> reviews = repository.findAll()
                .stream()
                .filter(filterByCourse(courseId))
                .filter(filterByStudent(studentId))
                .map(FindReviewQueryResponse::new)
                .collect(Collectors.toList());

        return new FindReviewsQueryResponse(reviews);
    }

    private Predicate<Review> filterByCourse(String courseId) {
        return review -> {
            if (courseId != null) {
                return courseId.equals(review.getCourseId().getValue());
            } else {
                return true;
            }
        };
    }

    private Predicate<Review> filterByStudent(String studentId) {
        return review -> {
            if (studentId != null) {
                return studentId.equals(review.getStudentId().getValue());
            } else {
                return true;
            }
        };
    }
}
