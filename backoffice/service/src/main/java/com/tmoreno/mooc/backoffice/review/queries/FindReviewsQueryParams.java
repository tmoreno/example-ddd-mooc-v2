package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.shared.query.QueryParams;

public final class FindReviewsQueryParams extends QueryParams {

    private String courseId;
    private String studentId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
