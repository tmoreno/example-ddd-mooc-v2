package com.tmoreno.mooc.backoffice.teacher.controllers;

import com.tmoreno.mooc.backoffice.teacher.queries.FindTeacherQuery;
import com.tmoreno.mooc.backoffice.teacher.queries.FindTeacherQueryParams;
import com.tmoreno.mooc.backoffice.teacher.queries.FindTeacherQueryResponse;
import com.tmoreno.mooc.backoffice.teacher.queries.FindTeachersQuery;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public final class TeacherGetController {

    private final FindTeacherQuery findTeacherQuery;
    private final FindTeachersQuery findTeachersQuery;

    public TeacherGetController(FindTeacherQuery findTeacherQuery, FindTeachersQuery findTeachersQuery) {
        this.findTeacherQuery = findTeacherQuery;
        this.findTeachersQuery = findTeachersQuery;
    }

    @GetMapping(value = "/teachers")
    public List<FindTeacherQueryResponse> getAll() {
        return findTeachersQuery.execute(new VoidQueryParams()).getTeachers();
    }

    @GetMapping(value = "/teachers/{id}")
    public FindTeacherQueryResponse getById(@PathVariable String id) {
        FindTeacherQueryParams params = new FindTeacherQueryParams();
        params.setTeacherId(id);

        return findTeacherQuery.execute(params);
    }
}
