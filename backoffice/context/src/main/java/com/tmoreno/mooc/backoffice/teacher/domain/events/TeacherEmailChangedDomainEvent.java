package com.tmoreno.mooc.backoffice.teacher.domain.events;

import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;

public final class TeacherEmailChangedDomainEvent extends DomainEvent {

    private final TeacherId teacherId;
    private final Email email;

    public TeacherEmailChangedDomainEvent(TeacherId teacherId, Email email) {
        this.teacherId = teacherId;
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

    public String getTeacherId() {
        return teacherId.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }
}
