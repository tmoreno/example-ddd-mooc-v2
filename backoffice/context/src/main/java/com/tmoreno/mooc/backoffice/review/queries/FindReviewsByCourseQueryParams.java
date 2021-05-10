package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.shared.query.QueryParams;

public final class FindReviewsByCourseQueryParams extends QueryParams {

    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
