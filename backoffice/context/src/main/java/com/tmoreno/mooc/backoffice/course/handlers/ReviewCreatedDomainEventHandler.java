package com.tmoreno.mooc.backoffice.course.handlers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class ReviewCreatedDomainEventHandler implements EventHandler<ReviewCreatedDomainEvent> {

    private final CourseRepository repository;

    public ReviewCreatedDomainEventHandler(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(ReviewCreatedDomainEvent event) {
        ReviewId reviewId = event.getReviewId();
        CourseId courseId = event.getCourseId();
        StudentId studentId = event.getStudentId();

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.addReview(reviewId, studentId);

        repository.save(course);
    }
}