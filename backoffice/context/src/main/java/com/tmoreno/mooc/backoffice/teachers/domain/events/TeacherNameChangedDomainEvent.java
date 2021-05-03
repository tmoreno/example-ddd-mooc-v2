package com.tmoreno.mooc.backoffice.teachers.domain.events;

import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;

public final class TeacherNameChangedDomainEvent extends DomainEvent {
    private final PersonName name;

    public TeacherNameChangedDomainEvent(TeacherId teacherId, PersonName name) {
        super(teacherId);

        this.name = name;
    }

    @Override
    public String getEventName() {
        return "teacher.name.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public PersonName getName() {
        return name;
    }
}
