package com.tmoreno.mooc.backoffice.student.queries;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.Map;
import java.util.Set;

public final class FindStudentQueryResponse extends QueryResponse {

    private final String name;
    private final String email;
    private final Set<CourseId> courses;
    private final Map<CourseId, ReviewId> reviews;

    public FindStudentQueryResponse(Student student) {
        this.name = student.getName().getValue();
        this.email = student.getEmail().getValue();
        this.courses = student.getCourses();
        this.reviews = student.getReviews();
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

    public Map<CourseId, ReviewId> getReviews() {
        return reviews;
    }
}
