package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentCreatedDomainEvent;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.handlers.EventHandler;

public final class StudentCreatedDomainEventHandler implements EventHandler<StudentCreatedDomainEvent> {

    private final StudentRepository repository;

    public StudentCreatedDomainEventHandler(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(StudentCreatedDomainEvent event) {
        StudentId studentId = new StudentId(event.getStudentId());
        PersonName name = new PersonName(event.getName());
        Email email = new Email(event.getEmail());

        Student student = new Student(studentId, name, email);

        repository.save(student);
    }
}
