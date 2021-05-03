package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.ReviewId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseReviewDeletedDomainEvent extends DomainEvent {
    private final ReviewId reviewId;

    public CourseReviewDeletedDomainEvent(CourseId courseId, ReviewId reviewId) {
        super(courseId);

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

    public ReviewId getReviewId() {
        return reviewId;
    }
}
