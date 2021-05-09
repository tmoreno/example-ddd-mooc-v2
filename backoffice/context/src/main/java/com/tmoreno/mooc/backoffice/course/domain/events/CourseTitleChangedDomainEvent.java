package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseTitleChangedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final CourseTitle title;

    public CourseTitleChangedDomainEvent(CourseId courseId, CourseTitle title) {
        this.courseId = courseId;
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

    public CourseId getCourseId() {
        return courseId;
    }

    public CourseTitle getTitle() {
        return title;
    }
}
