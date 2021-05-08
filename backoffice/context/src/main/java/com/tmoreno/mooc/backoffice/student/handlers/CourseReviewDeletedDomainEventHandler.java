package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseReviewDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.review.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class CourseReviewDeletedDomainEventHandler implements EventHandler<CourseReviewDeletedDomainEvent> {

    private final StudentRepository repository;

    public CourseReviewDeletedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseReviewDeletedDomainEvent event) {
        CourseId courseId = new CourseId(event.getAggregateId());
        ReviewId reviewId = event.getReviewId();
        StudentId studentId = event.getStudentId();

        Student student = repository.find(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));

        student.deleteReview(courseId, reviewId);

        repository.save(student);
    }
}
