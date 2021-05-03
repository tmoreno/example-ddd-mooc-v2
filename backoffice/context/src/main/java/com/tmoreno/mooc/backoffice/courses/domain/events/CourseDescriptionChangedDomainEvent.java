package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseDescriptionChangedDomainEvent extends DomainEvent {
    private final CourseDescription description;

    public CourseDescriptionChangedDomainEvent(CourseId courseId, CourseDescription description) {
        super(courseId);

        this.description = description;
    }

    @Override
    public String getEventName() {
        return "course.description.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseDescription getDescription() {
        return description;
    }
}
