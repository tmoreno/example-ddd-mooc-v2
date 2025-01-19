package com.tmoreno.mooc.frontoffice.teacher.queries;

import com.tmoreno.mooc.frontoffice.teacher.domain.CourseId;
import com.tmoreno.mooc.frontoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.Set;
import java.util.stream.Collectors;

public record FindTeacherQueryResponse(
    String id,
    String name,
    String email,
    Set<String> courses
) implements QueryResponse {

    public static FindTeacherQueryResponse from(Teacher teacher) {
        return new FindTeacherQueryResponse(
            teacher.getId().getValue(),
            teacher.getName().value(),
            teacher.getEmail().value(),
            teacher.getCourses().stream().map(CourseId::getValue).collect(Collectors.toSet())
        );
    }
}
