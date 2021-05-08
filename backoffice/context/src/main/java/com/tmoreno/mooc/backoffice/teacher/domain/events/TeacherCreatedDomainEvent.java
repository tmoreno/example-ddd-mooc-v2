package com.tmoreno.mooc.backoffice.teacher.domain.events;

import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;

public final class TeacherCreatedDomainEvent extends DomainEvent {

    private final PersonName name;
    private final Email email;

    public TeacherCreatedDomainEvent(TeacherId teacherId, PersonName name, Email email) {
        super(teacherId);

        this.name = name;
        this.email = email;
    }

    @Override
    public String getEventName() {
        return "teacher.created";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public PersonName getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }
}
