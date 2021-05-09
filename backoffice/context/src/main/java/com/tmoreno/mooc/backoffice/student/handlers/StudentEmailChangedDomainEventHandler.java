package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentEmailChangedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class StudentEmailChangedDomainEventHandler implements EventHandler<StudentEmailChangedDomainEvent> {

    private final StudentRepository repository;

    public StudentEmailChangedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(StudentEmailChangedDomainEvent event) {
        StudentId studentId = event.getStudentId();
        Email email = event.getEmail();

        Student student = repository.find(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));

        student.changeEmail(email);

        repository.save(student);
    }
}
