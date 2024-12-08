package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseCreatedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final CourseTitle title;

    public CourseCreatedDomainEvent(CourseId courseId, CourseTitle title) {
        this.courseId = courseId;
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

    public String getCourseId() {
        return courseId.getValue();
    }

    public String getTitle() {
        return title.getValue();
    }
}
