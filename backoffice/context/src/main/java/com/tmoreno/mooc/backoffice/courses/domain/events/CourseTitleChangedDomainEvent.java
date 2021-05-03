package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.CourseTitle;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseTitleChangedDomainEvent extends DomainEvent {
    private final CourseTitle title;

    public CourseTitleChangedDomainEvent(CourseId courseId, CourseTitle title) {
        super(courseId);

        this.title = title;
    }

    @Override
    public String getEventName() {
        return "course.title.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseTitle getTitle() {
        return title;
    }
}
