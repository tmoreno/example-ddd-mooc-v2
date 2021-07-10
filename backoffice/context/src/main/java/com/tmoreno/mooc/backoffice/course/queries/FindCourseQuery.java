package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.query.Query;

public final class FindCourseQuery implements Query<FindCourseQueryParams, FindCourseQueryResponse> {

    private final CourseRepository repository;

    public FindCourseQuery(CourseRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public FindCourseQueryResponse execute(FindCourseQueryParams params) {
        CourseId id = new CourseId(params.getCourseId());

        Course course = repository.find(id).orElseThrow(() -> new CourseNotFoundException(id));

        return new FindCourseQueryResponse(course);
    }
}
