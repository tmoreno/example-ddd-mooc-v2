package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.queries.FindCourseQuery;
import com.tmoreno.mooc.backoffice.course.queries.FindCourseQueryParams;
import com.tmoreno.mooc.backoffice.course.queries.FindCourseQueryResponse;
import com.tmoreno.mooc.backoffice.course.queries.FindCoursesQuery;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseGetController {

    private final FindCourseQuery findCourseQuery;
    private final FindCoursesQuery findCoursesQuery;

    public CourseGetController(FindCourseQuery findCourseQuery, FindCoursesQuery findCoursesQuery) {
        this.findCourseQuery = findCourseQuery;
        this.findCoursesQuery = findCoursesQuery;
    }

    @GetMapping(value = "/courses")
    public List<FindCourseQueryResponse> getAll() {
        return findCoursesQuery.execute(new VoidQueryParams()).getCourses();
    }

    @GetMapping(value = "/courses/{id}")
    public FindCourseQueryResponse getById(@PathVariable String id) {
        FindCourseQueryParams params = new FindCourseQueryParams();
        params.setCourseId(id);

        return findCourseQuery.execute(params);
    }
}
