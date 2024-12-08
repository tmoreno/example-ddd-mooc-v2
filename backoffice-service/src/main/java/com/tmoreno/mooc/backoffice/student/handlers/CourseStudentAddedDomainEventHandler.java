package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.course.domain.events.CourseStudentAddedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.handlers.EventHandler;

public final class CourseStudentAddedDomainEventHandler implements EventHandler<CourseStudentAddedDomainEvent> {

    private final StudentRepository repository;

    public CourseStudentAddedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseStudentAddedDomainEvent event) {
        CourseId courseId = new CourseId(event.getCourseId());
        StudentId studentId = new StudentId(event.getStudentId());

        Student student = repository.find(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));

        student.addCourse(courseId);

        repository.save(student);
    }
}
