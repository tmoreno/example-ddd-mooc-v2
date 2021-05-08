package com.tmoreno.mooc.backoffice.student.domain.events;

import com.tmoreno.mooc.backoffice.review.ReviewId;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentReviewDeletedDomainEvent extends DomainEvent {
    private final ReviewId reviewId;

    public StudentReviewDeletedDomainEvent(StudentId studentId, ReviewId reviewId) {
        super(studentId);

        this.reviewId = reviewId;
    }


    @Override
    public String getEventName() {
        return "student.review.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public ReviewId getReviewId() {
        return reviewId;
    }
}
