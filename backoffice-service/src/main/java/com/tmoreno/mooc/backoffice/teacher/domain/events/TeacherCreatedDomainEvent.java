package com.tmoreno.mooc.backoffice.teacher.domain.events;

import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class TeacherCreatedDomainEvent extends DomainEvent {

    private final TeacherId teacherId;
    private final PersonName name;
    private final Email email;

    public TeacherCreatedDomainEvent(TeacherId teacherId, PersonName name, Email email) {
        this.teacherId = teacherId;
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

    public String getTeacherId() {
        return teacherId.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getEmail() {
        return email.value();
    }
}
