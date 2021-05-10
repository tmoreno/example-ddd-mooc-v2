package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public final class FindReviewsByStudentQuery implements Query<FindReviewsByStudentQueryParams, FindReviewsQueryResponse> {

    private final ReviewRepository repository;

    public FindReviewsByStudentQuery(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public FindReviewsQueryResponse execute(FindReviewsByStudentQueryParams params) {
        StudentId studentId = new StudentId(params.getStudentId());

        List<FindReviewQueryResponse> reviews = repository.findByStudent(studentId)
                .stream()
                .map(FindReviewQueryResponse::new)
                .collect(Collectors.toList());

        return new FindReviewsQueryResponse(reviews);
    }
}
