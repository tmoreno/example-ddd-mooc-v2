package com.tmoreno.mooc.backoffice.students.domain.events;

import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;

public final class StudentNameChangedDomainEvent extends DomainEvent {
    private final PersonName name;

    public StudentNameChangedDomainEvent(StudentId studentId, PersonName name) {
        super(studentId);

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

    public PersonName getName() {
        return name;
    }
}
