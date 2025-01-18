package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.List;

public final class FindCoursesQueryResponse implements QueryResponse {

    private final List<FindCourseQueryResponse> courses;

    public FindCoursesQueryResponse(List<FindCourseQueryResponse> courses) {
        this.courses = courses;
    }

    public List<FindCourseQueryResponse> getCourses() {
        return courses;
    }
}
