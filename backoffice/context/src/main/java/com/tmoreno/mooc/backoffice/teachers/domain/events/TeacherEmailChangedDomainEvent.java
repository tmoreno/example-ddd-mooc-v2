package com.tmoreno.mooc.backoffice.teachers.domain.events;

import com.tmoreno.mooc.backoffice.shared.domain.Email;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;

public final class TeacherEmailChangedDomainEvent extends DomainEvent {
    private final Email email;

    public TeacherEmailChangedDomainEvent(TeacherId teacherId, Email email) {
        super(teacherId);
        this.email = email;
    }

    @Override
    public String getEventName() {
        return "teacher.email.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public Email getEmail() {
        return email;
    }
}
