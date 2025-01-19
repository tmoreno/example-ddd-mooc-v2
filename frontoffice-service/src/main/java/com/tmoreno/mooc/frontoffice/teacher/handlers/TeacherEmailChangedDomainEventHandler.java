package com.tmoreno.mooc.frontoffice.teacher.handlers;

import com.tmoreno.mooc.frontoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.frontoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.frontoffice.teacher.domain.events.TeacherEmailChangedDomainEvent;
import com.tmoreno.mooc.frontoffice.teacher.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.handlers.EventHandler;

public final class TeacherEmailChangedDomainEventHandler implements EventHandler<TeacherEmailChangedDomainEvent> {

    private final TeacherRepository repository;

    public TeacherEmailChangedDomainEventHandler(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(TeacherEmailChangedDomainEvent event) {
        TeacherId id = new TeacherId(event.getTeacherId());
        Email email = new Email(event.getEmail());

        Teacher teacher = repository.find(id).orElseThrow(() -> new TeacherNotFoundException(id));

        teacher.changeEmail(email);

        repository.save(teacher);
    }
}
