package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.handlers.EventHandler;

public final class ReviewCreatedDomainEventHandler implements EventHandler<ReviewCreatedDomainEvent> {

    private final StudentRepository repository;

    public ReviewCreatedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(ReviewCreatedDomainEvent event) {
        CourseId courseId = new CourseId(event.getCourseId());
        ReviewId reviewId = new ReviewId(event.getReviewId());
        StudentId studentId = new StudentId(event.getStudentId());

        Student student = repository.find(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));

        student.addReview(courseId, reviewId);

        repository.save(student);
    }
}
