package com.tmoreno.mooc.backoffice.review.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class ReviewDeletedDomainEvent extends DomainEvent {

    private final ReviewId reviewId;
    private final CourseId courseId;
    private final StudentId studentId;

    public ReviewDeletedDomainEvent(ReviewId reviewId, CourseId courseId, StudentId studentId) {
        this.reviewId = reviewId;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    @Override
    public String getEventName() {
        return "review.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public ReviewId getReviewId() {
        return reviewId;
    }

    public CourseId getCourseId() {
        return courseId;
    }

    public StudentId getStudentId() {
        return studentId;
    }
}
