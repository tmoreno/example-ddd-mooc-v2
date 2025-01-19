package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseDescriptionChangedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final CourseDescription description;

    public CourseDescriptionChangedDomainEvent(CourseId courseId, CourseDescription description) {
        this.courseId = courseId;
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

    public String getCourseId() {
        return courseId.getValue();
    }

    public String getDescription() {
        return description.value();
    }
}
