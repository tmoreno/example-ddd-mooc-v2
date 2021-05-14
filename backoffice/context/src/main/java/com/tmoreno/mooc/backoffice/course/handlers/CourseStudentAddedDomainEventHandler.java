package com.tmoreno.mooc.backoffice.course.handlers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseStudentAddedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class CourseStudentAddedDomainEventHandler implements EventHandler<CourseStudentAddedDomainEvent> {

    private final CourseRepository repository;

    public CourseStudentAddedDomainEventHandler(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseStudentAddedDomainEvent event) {
        CourseId courseId = event.getCourseId();
        StudentId studentId = event.getStudentId();

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.addStudent(studentId);

        repository.save(course);
    }
}
