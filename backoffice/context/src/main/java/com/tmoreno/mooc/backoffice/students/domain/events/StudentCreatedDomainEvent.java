package com.tmoreno.mooc.backoffice.students.domain.events;

import com.tmoreno.mooc.backoffice.shared.domain.Email;
import com.tmoreno.mooc.backoffice.shared.domain.PersonName;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;

public final class StudentCreatedDomainEvent extends DomainEvent {
    private final PersonName name;
    private final Email email;

    public StudentCreatedDomainEvent(StudentId studentId, PersonName name, Email email) {
        super(studentId);

        this.name = name;
        this.email = email;
    }

    @Override
    public String getEventName() {
        return "student.created";
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
