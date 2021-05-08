package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.ReviewId;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseReviewAddedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class CourseReviewAddedDomainEventHandler implements EventHandler<CourseReviewAddedDomainEvent> {

    private final StudentRepository repository;

    public CourseReviewAddedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseReviewAddedDomainEvent event) {
        CourseId courseId = new CourseId(event.getAggregateId());
        ReviewId reviewId = event.getReviewId();
        StudentId studentId = event.getStudentId();

        Student student = repository.find(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));

        student.addReview(reviewId);

        repository.save(student);
    }
}
