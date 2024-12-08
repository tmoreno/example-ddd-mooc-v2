package com.tmoreno.mooc.backoffice.student.queries;

import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.List;

public final class FindStudentsQueryResponse extends QueryResponse {

    private final List<FindStudentQueryResponse> students;

    public FindStudentsQueryResponse(List<FindStudentQueryResponse> students) {
        this.students = students;
    }

    public List<FindStudentQueryResponse> getStudents() {
        return students;
    }
}
