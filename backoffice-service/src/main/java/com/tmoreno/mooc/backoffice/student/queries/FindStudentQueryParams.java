package com.tmoreno.mooc.backoffice.student.queries;

import com.tmoreno.mooc.shared.query.QueryParams;

public final class FindStudentQueryParams implements QueryParams {

    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
