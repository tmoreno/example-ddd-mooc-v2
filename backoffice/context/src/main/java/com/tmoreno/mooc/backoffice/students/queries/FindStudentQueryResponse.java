package com.tmoreno.mooc.backoffice.students.queries;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.ReviewId;
import com.tmoreno.mooc.backoffice.students.domain.Student;
import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.Set;

public final class FindStudentQueryResponse extends QueryResponse {

    private final String name;
    private final String email;
    private final Set<CourseId> courses;
    private final Set<ReviewId> reviews;

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

    public Set<ReviewId> getReviews() {
        return reviews;
    }
}
