package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;

public final class CourseDiscardedDomainEvent extends DomainEvent {
    public CourseDiscardedDomainEvent(CourseId courseId) {
        super(courseId);
    }

    @Override
    public String getEventName() {
        return "course.discarded";
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
