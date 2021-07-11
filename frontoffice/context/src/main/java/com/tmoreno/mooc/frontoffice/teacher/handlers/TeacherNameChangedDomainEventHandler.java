package com.tmoreno.mooc.frontoffice.teacher.handlers;

import com.tmoreno.mooc.frontoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.frontoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.frontoffice.teacher.domain.events.TeacherEmailChangedDomainEvent;
import com.tmoreno.mooc.frontoffice.teacher.domain.events.TeacherNameChangedDomainEvent;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.exceptions.teacher.TeacherNotFoundException;
import com.tmoreno.mooc.shared.handlers.EventHandler;

public final class TeacherNameChangedDomainEventHandler implements EventHandler<TeacherNameChangedDomainEvent> {

    private final TeacherRepository repository;

    public TeacherNameChangedDomainEventHandler(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(TeacherNameChangedDomainEvent event) {
        TeacherId id = new TeacherId(event.getTeacherId());
        PersonName name = new PersonName(event.getName());

        Teacher teacher = repository.find(id).orElseThrow(() -> new TeacherNotFoundException(id));

        teacher.changeName(name);

        repository.save(teacher);
    }
}
