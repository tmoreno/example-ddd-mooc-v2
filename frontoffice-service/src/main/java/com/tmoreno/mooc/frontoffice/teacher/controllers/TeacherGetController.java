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
    public List<Teacher> getAll() {
        return findTeachersQuery.execute(new VoidQueryParams())
            .getTeachers()
            .stream()
            .map(Teacher::from)
            .toList();
    }

    @GetMapping(value = "/teachers/{id}")
    public Teacher getById(@PathVariable String id) {
        FindTeacherQueryParams params = new FindTeacherQueryParams();
        params.setTeacherId(id);

        return Teacher.from(findTeacherQuery.execute(params));
    }

    public record Teacher(
        String id,
        String name,
        String email,
        Set<String> courses
    ) {
        public static Teacher from(FindTeacherQueryResponse response) {
            return new Teacher(response.getId(), response.getName(), response.getEmail(), response.getCourses());
        }
    }
}
