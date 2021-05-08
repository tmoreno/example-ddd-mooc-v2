package com.tmoreno.mooc.backoffice.students.handlers;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.events.CourseStudentAddedDomainEvent;
import com.tmoreno.mooc.backoffice.students.domain.Student;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;
import com.tmoreno.mooc.backoffice.students.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.students.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class CourseStudentAddedDomainEventHandler implements EventHandler<CourseStudentAddedDomainEvent> {

    private final StudentRepository repository;

    public CourseStudentAddedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseStudentAddedDomainEvent event) {
        CourseId courseId = new CourseId(event.getAggregateId());
        StudentId studentId = event.getStudentId();

        Student student = repository.find(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));

        student.addCourse(courseId);

        repository.save(student);
    }
}
