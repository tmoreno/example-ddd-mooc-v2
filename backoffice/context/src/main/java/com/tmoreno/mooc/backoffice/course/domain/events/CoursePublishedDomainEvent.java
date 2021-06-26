package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CoursePublishedDomainEvent extends DomainEvent {

    private final CourseId courseId;

    public CoursePublishedDomainEvent(CourseId courseId) {
        this.courseId = courseId;
    }

    @Override
    public String getEventName() {
        return "course.published";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public String getCourseId() {
        return courseId.getValue();
    }
}
