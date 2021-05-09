package com.tmoreno.mooc.backoffice.student.domain.events;

import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentCreatedDomainEvent extends DomainEvent {

    private final StudentId studentId;
    private final PersonName name;
    private final Email email;

    public StudentCreatedDomainEvent(StudentId studentId, PersonName name, Email email) {
        this.studentId = studentId;
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

    public StudentId getStudentId() {
        return studentId;
    }

    public PersonName getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }
}
