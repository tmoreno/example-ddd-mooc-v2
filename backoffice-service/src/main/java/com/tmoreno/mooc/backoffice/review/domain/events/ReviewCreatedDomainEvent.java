package com.tmoreno.mooc.backoffice.review.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRating;
import com.tmoreno.mooc.backoffice.review.domain.ReviewText;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.CreatedOn;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class ReviewCreatedDomainEvent extends DomainEvent {

    private final ReviewId reviewId;
    private final CourseId courseId;
    private final StudentId studentId;
    private final ReviewRating rating;
    private final ReviewText text;
    private final CreatedOn createdOn;

    public ReviewCreatedDomainEvent(
            ReviewId reviewId,
            CourseId courseId,
            StudentId studentId,
            ReviewRating rating,
            ReviewText text,
            CreatedOn createdOn
    ) {
        this.reviewId = reviewId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.rating = rating;
        this.text = text;
        this.createdOn = createdOn;
    }

    @Override
    public String getEventName() {
        return "review.created";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public String getReviewId() {
        return reviewId.getValue();
    }

    public String getCourseId() {
        return courseId.getValue();
    }

    public String getStudentId() {
        return studentId.getValue();
    }

    public String getRating() {
        return rating.name();
    }

    public String getText() {
        return text.value();
    }

    public long getCreatedOn() {
        return createdOn.getValue();
    }
}
