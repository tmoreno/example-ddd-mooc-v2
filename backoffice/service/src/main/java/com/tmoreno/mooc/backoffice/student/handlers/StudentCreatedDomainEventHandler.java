package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentCreatedDomainEvent;
import com.tmoreno.mooc.shared.handlers.EventHandler;

import java.util.Collections;

public final class StudentCreatedDomainEventHandler implements EventHandler<StudentCreatedDomainEvent> {

    private final StudentRepository repository;

    public StudentCreatedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(StudentCreatedDomainEvent event) {
        Student student = Student.restore(
            event.getStudentId(),
            event.getName(),
            event.getEmail(),
            Collections.emptySet(),
            Collections.emptyMap()
        );

        repository.save(student);
    }
}
