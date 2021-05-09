package com.tmoreno.mooc.backoffice.student.domain.events;

import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;

public final class StudentNameChangedDomainEvent extends DomainEvent {

    private final StudentId studentId;
    private final PersonName name;

    public StudentNameChangedDomainEvent(StudentId studentId, PersonName name) {
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public String getEventName() {
        return "student.name.changed";
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
}
