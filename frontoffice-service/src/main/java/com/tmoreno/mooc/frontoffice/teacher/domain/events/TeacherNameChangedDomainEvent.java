package com.tmoreno.mooc.frontoffice.teacher.domain.events;

import com.tmoreno.mooc.shared.events.DomainEvent;

public final class TeacherNameChangedDomainEvent extends DomainEvent {

    private String teacherId;
    private String name;

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public int getVersion() {
        return version;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
