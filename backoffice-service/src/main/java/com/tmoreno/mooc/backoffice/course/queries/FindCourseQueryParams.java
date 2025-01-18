package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.shared.query.QueryParams;

public final class FindCourseQueryParams implements QueryParams {

    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
