package com.tmoreno.mooc.backoffice.students.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.ReviewId;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;

public final class StudentReviewAddedDomainEvent extends DomainEvent {
    private final ReviewId reviewId;

    public StudentReviewAddedDomainEvent(StudentId studentId, ReviewId reviewId) {
        super(studentId);

        this.reviewId = reviewId;
    }

    @Override
    public String getEventName() {
        return "student.review.added";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public ReviewId getReviewId() {
        return reviewId;
    }
}