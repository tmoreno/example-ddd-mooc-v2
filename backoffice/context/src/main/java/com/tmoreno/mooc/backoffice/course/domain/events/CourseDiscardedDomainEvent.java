package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseDiscardedDomainEvent extends DomainEvent {

    private final CourseId courseId;

    public CourseDiscardedDomainEvent(CourseId courseId) {
        this.courseId = courseId;
    }

    @Override
    public String getEventName() {
        return "course.discarded";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public String getCourseId() {
        return courseId.getValue();
    }
}
