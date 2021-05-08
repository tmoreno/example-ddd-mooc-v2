package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseReviewDeletedDomainEvent extends DomainEvent {
    private final StudentId studentId;
    private final ReviewId reviewId;

    public CourseReviewDeletedDomainEvent(CourseId courseId, StudentId studentId, ReviewId reviewId) {
        super(courseId);

        this.studentId = studentId;
        this.reviewId = reviewId;
    }

    @Override
    public String getEventName() {
        return "course.review.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public ReviewId getReviewId() {
        return reviewId;
    }
}
