package com.tmoreno.mooc.frontoffice.teacher.domain.events;

import com.tmoreno.mooc.shared.events.DomainEvent;

public final class TeacherCreatedDomainEvent extends DomainEvent {

    private String name;
    private String email;
    private String teacherId;

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
