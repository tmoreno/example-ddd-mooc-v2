package com.tmoreno.mooc.backoffice.student.domain.events;

import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.DomainEvent;

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

    public String getStudentId() {
        return studentId.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }
}
