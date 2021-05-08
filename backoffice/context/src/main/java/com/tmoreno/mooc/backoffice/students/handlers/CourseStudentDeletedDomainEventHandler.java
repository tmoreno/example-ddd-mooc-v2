package com.tmoreno.mooc.backoffice.students.handlers;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.events.CourseStudentDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.students.domain.Student;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;
import com.tmoreno.mooc.backoffice.students.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.students.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class CourseStudentDeletedDomainEventHandler implements EventHandler<CourseStudentDeletedDomainEvent> {

    private final StudentRepository repository;

    public CourseStudentDeletedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CourseStudentDeletedDomainEvent event) {
        CourseId courseId = new CourseId(event.getAggregateId());
        StudentId studentId = event.getStudentId();

        Student student = repository.find(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));

        student.deleteCourse(courseId);

        repository.save(student);
    }
}
