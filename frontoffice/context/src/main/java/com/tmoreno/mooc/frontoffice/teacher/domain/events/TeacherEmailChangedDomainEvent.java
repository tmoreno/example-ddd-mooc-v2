package com.tmoreno.mooc.frontoffice.teacher.domain.events;

import com.tmoreno.mooc.shared.events.DomainEvent;

public final class TeacherEmailChangedDomainEvent extends DomainEvent {

    private String teacherId;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
