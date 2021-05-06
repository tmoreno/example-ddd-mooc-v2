package com.tmoreno.mooc.backoffice.students.queries;

import com.tmoreno.mooc.shared.query.QueryParams;

public final class FindStudentQueryParams extends QueryParams {

    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
