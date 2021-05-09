package com.tmoreno.mooc.backoffice.student.domain.events;

import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentEmailChangedDomainEvent extends DomainEvent {

    private final StudentId studentId;
    private final Email email;

    public StudentEmailChangedDomainEvent(StudentId studentId, Email email) {
        this.studentId = studentId;
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

    public StudentId getStudentId() {
        return studentId;
    }

    public Email getEmail() {
        return email;
    }
}
