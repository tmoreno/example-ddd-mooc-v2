package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentCreatedDomainEvent;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.EventHandler;

public final class StudentCreatedDomainEventHandler implements EventHandler<StudentCreatedDomainEvent> {

    private final StudentRepository repository;

    public StudentCreatedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(StudentCreatedDomainEvent event) {
        StudentId studentId = new StudentId(event.getAggregateId());
        PersonName name = event.getName();
        Email email = event.getEmail();

        Student student = Student.create(studentId, name, email);

        repository.save(student);
    }
}
