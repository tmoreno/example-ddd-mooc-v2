package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.CourseTitle;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseCreatedDomainEvent extends DomainEvent {
    private final CourseTitle title;

    public CourseCreatedDomainEvent(CourseId courseId, CourseTitle title) {
        super(courseId);

        this.title = title;
    }

    @Override
    public String getEventName() {
        return "course.created";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseTitle getTitle() {
        return title;
    }
}
