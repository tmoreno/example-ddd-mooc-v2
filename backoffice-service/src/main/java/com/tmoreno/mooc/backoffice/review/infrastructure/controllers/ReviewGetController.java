package com.tmoreno.mooc.backoffice.review.infrastructure.controllers;

import com.tmoreno.mooc.backoffice.review.queries.FindReviewQuery;
import com.tmoreno.mooc.backoffice.review.queries.FindReviewQueryParams;
import com.tmoreno.mooc.backoffice.review.queries.FindReviewQueryResponse;
import com.tmoreno.mooc.backoffice.review.queries.FindReviewsQuery;
import com.tmoreno.mooc.backoffice.review.queries.FindReviewsQueryParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewGetController {

    private final FindReviewQuery findReviewQuery;
    private final FindReviewsQuery findReviewsQuery;

    public ReviewGetController(FindReviewQuery findReviewQuery, FindReviewsQuery findReviewsQuery) {
        this.findReviewQuery = findReviewQuery;
        this.findReviewsQuery = findReviewsQuery;
    }

    @GetMapping(value = "/reviews")
    public List<FindReviewQueryResponse> getAll(
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String studentId
    ) {
        FindReviewsQueryParams params = new FindReviewsQueryParams();
        params.setCourseId(courseId);
        params.setStudentId(studentId);

        return findReviewsQuery.execute(params).getReviews();
    }

    @GetMapping(value = "/reviews/{id}")
    public FindReviewQueryResponse getById(@PathVariable String id) {
        FindReviewQueryParams params = new FindReviewQueryParams();
        params.setReviewId(id);

        return findReviewQuery.execute(params);
    }
}
