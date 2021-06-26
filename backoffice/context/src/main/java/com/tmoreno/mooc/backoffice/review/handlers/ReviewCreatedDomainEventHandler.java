package com.tmoreno.mooc.backoffice.review.handlers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRating;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.backoffice.review.domain.ReviewText;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.CreatedOn;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class ReviewCreatedDomainEventHandler implements EventHandler<ReviewCreatedDomainEvent> {

    private final ReviewRepository repository;

    public ReviewCreatedDomainEventHandler(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(ReviewCreatedDomainEvent event) {
        Review review = new Review(
                new ReviewId(event.getReviewId()),
                new CourseId(event.getCourseId()),
                new StudentId(event.getStudentId()),
                ReviewRating.valueOf(event.getRating()),
                new ReviewText(event.getText()),
                new CreatedOn(event.getCreatedOn())
        );

        repository.save(review);
    }
}
