package com.tmoreno.mooc.backoffice.students.domain.events;

import com.tmoreno.mooc.backoffice.shared.domain.Email;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;

public final class StudentEmailChangedDomainEvent extends DomainEvent {
    private final Email email;

    public StudentEmailChangedDomainEvent(StudentId studentId, Email email) {
        super(studentId);

        this.email = email;
    }

    @Override
    public String getEventName() {
        return "student.email.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public Email getEmail() {
        return email;
    }
}
