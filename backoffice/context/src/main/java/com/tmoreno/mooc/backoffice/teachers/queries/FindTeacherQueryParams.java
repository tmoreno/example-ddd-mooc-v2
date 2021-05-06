package com.tmoreno.mooc.backoffice.teachers.queries;

import com.tmoreno.mooc.shared.query.QueryParams;

public final class FindTeacherQueryParams extends QueryParams {

    private String teacherId;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
