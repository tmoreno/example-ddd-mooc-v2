package com.tmoreno.mooc.backoffice.teacher.queries;

import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.Set;
import java.util.stream.Collectors;

public final class FindTeacherQueryResponse implements QueryResponse {

    private final String id;
    private final String name;
    private final String email;
    private final Set<String> courses;

    public FindTeacherQueryResponse(Teacher teacher) {
        this.id = teacher.getId().getValue();
        this.name = teacher.getName().value();
        this.email = teacher.getEmail().value();
        this.courses = teacher.getCourses().stream().map(Identifier::getValue).collect(Collectors.toSet());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getCourses() {
        return courses;
    }
}
