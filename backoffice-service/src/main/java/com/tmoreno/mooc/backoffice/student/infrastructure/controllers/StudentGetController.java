package com.tmoreno.mooc.backoffice.student.infrastructure.controllers;

import com.tmoreno.mooc.backoffice.student.queries.FindStudentQuery;
import com.tmoreno.mooc.backoffice.student.queries.FindStudentQueryParams;
import com.tmoreno.mooc.backoffice.student.queries.FindStudentQueryResponse;
import com.tmoreno.mooc.backoffice.student.queries.FindStudentsQuery;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentGetController {

    private final FindStudentQuery findStudentQuery;
    private final FindStudentsQuery findStudentsQuery;

    public StudentGetController(FindStudentQuery findStudentQuery, FindStudentsQuery findStudentsQuery) {
        this.findStudentQuery = findStudentQuery;
        this.findStudentsQuery = findStudentsQuery;
    }

    @GetMapping(value = "/students")
    public List<FindStudentQueryResponse> getAll() {
        return findStudentsQuery.execute(new VoidQueryParams()).getStudents();
    }

    @GetMapping(value = "/students/{id}")
    public FindStudentQueryResponse getById(@PathVariable String id) {
        FindStudentQueryParams params = new FindStudentQueryParams();
        params.setStudentId(id);

        return findStudentQuery.execute(params);
    }
}
