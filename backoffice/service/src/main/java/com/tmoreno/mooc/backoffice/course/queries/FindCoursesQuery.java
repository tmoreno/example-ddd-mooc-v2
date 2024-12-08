package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.shared.query.Query;
import com.tmoreno.mooc.shared.query.VoidQueryParams;

import java.util.List;
import java.util.stream.Collectors;

public final class FindCoursesQuery implements Query<VoidQueryParams, FindCoursesQueryResponse> {

    private final CourseRepository repository;

    public FindCoursesQuery(CourseRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public FindCoursesQueryResponse execute(VoidQueryParams params) {
        List<FindCourseQueryResponse> courses = repository.findAll()
                .stream()
                .map(FindCourseQueryResponse::new)
                .collect(Collectors.toList());

        return new FindCoursesQueryResponse(courses);
    }
}
