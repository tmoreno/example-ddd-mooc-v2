package com.tmoreno.mooc.frontoffice.teacher.controllers;

import com.tmoreno.mooc.frontoffice.teacher.queries.FindTeacherQuery;
import com.tmoreno.mooc.frontoffice.teacher.queries.FindTeacherQueryParams;
import com.tmoreno.mooc.frontoffice.teacher.queries.FindTeacherQueryResponse;
import com.tmoreno.mooc.frontoffice.teacher.queries.FindTeachersQuery;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class TeacherGetController {

    private final FindTeacherQuery findTeacherQuery;
    private final FindTeachersQuery findTeachersQuery;

    public TeacherGetController(FindTeacherQuery findTeacherQuery, FindTeachersQuery findTeachersQuery) {
        this.findTeacherQuery = findTeacherQuery;
        this.findTeachersQuery = findTeachersQuery;
    }

    @GetMapping(value = "/teachers")
    public List<TeacherResponseBody> getAll() {
        return findTeachersQuery.execute(new VoidQueryParams())
            .teachers()
            .stream()
            .map(TeacherResponseBody::from)
            .toList();
    }

    @GetMapping(value = "/teachers/{id}")
    public TeacherResponseBody getById(@PathVariable String id) {
        FindTeacherQueryParams params = new FindTeacherQueryParams(id);

        return TeacherResponseBody.from(findTeacherQuery.execute(params));
    }

    public record TeacherResponseBody(
        String id,
        String name,
        String email,
        Set<String> courses
    ) {
        public static TeacherResponseBody from(FindTeacherQueryResponse response) {
            return new TeacherResponseBody(response.id(), response.name(), response.email(), response.courses());
        }
    }
}
