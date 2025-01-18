package com.tmoreno.mooc.backoffice.student.queries;

import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class FindStudentQueryResponse implements QueryResponse {

    private final String id;
    private final String name;
    private final String email;
    private final Set<String> courses;
    private final List<CourseReviewResponse> reviews;

    public FindStudentQueryResponse(Student student) {
        this.id = student.getId().getValue();
        this.name = student.getName().value();
        this.email = student.getEmail().value();
        this.courses = student.getCourses().stream().map(Identifier::getValue).collect(Collectors.toSet());
        this.reviews = student.getReviews()
                .entrySet()
                .stream()
                .map(entry -> new CourseReviewResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
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

    public List<CourseReviewResponse> getReviews() {
        return reviews;
    }

    public static class CourseReviewResponse {
        private final String courseId;
        private final String reviewId;

        public CourseReviewResponse(CourseId courseId, ReviewId reviewId) {
            this.courseId = courseId.getValue();
            this.reviewId = reviewId.getValue();
        }

        public String getCourseId() {
            return courseId;
        }

        public String getReviewId() {
            return reviewId;
        }
    }
}
