package com.tmoreno.mooc.backoffice.teacher.queries;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.Set;

public final class FindTeacherQueryResponse extends QueryResponse {

    private final String name;
    private final String email;
    private final Set<CourseId> courses;

    public FindTeacherQueryResponse(Teacher teacher) {
        this.name = teacher.getName().getValue();
        this.email = teacher.getEmail().getValue();
        this.courses = teacher.getCourses();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<CourseId> getCourses() {
        return courses;
    }
}
