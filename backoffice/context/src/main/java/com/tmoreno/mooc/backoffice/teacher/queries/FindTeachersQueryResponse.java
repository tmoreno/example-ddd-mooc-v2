package com.tmoreno.mooc.backoffice.teacher.queries;

import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.List;

public final class FindTeachersQueryResponse extends QueryResponse {

    private final List<FindTeacherQueryResponse> teachers;

    public FindTeachersQueryResponse(List<FindTeacherQueryResponse> teachers) {
        this.teachers = teachers;
    }

    public List<FindTeacherQueryResponse> getTeachers() {
        return teachers;
    }
}
