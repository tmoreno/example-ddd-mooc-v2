package com.tmoreno.mooc.frontoffice.teacher.handlers;

import com.tmoreno.mooc.frontoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.frontoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.frontoffice.teacher.domain.events.TeacherCreatedDomainEvent;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.exceptions.teacher.TeacherExistsException;
import com.tmoreno.mooc.shared.handlers.EventHandler;

public final class TeacherCreatedDomainEventHandler implements EventHandler<TeacherCreatedDomainEvent> {
    private final TeacherRepository repository;

    public TeacherCreatedDomainEventHandler(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(TeacherCreatedDomainEvent event) {
        TeacherId id = new TeacherId(event.getTeacherId());
        PersonName name = new PersonName(event.getName());
        Email email = new Email(event.getEmail());

        if (repository.exists(id, email)) {
            throw new TeacherExistsException(id, email);
        }

        Teacher teacher = Teacher.create(id, name, email);

        repository.save(teacher);
    }
}
