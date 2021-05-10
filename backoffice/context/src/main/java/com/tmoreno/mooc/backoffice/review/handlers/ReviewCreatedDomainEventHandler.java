package com.tmoreno.mooc.backoffice.review.handlers;

import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewCreatedDomainEvent;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class ReviewCreatedDomainEventHandler implements EventHandler<ReviewCreatedDomainEvent> {

    private final ReviewRepository repository;

    public ReviewCreatedDomainEventHandler(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(ReviewCreatedDomainEvent event) {
        Review review = new Review(
                event.getReviewId(),
                event.getCourseId(),
                event.getStudentId(),
                event.getRating(),
                event.getText(),
                event.getCreatedOn()
        );

        repository.save(review);
    }
}
