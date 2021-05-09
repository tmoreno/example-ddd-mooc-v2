package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseSummary;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseSummaryChangedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final CourseSummary summary;

    public CourseSummaryChangedDomainEvent(CourseId courseId, CourseSummary summary) {
        this.courseId = courseId;
        this.summary = summary;
    }

    @Override
    public String getEventName() {
        return "course.summary.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseId getCourseId() {
        return courseId;
    }

    public CourseSummary getSummary() {
        return summary;
    }
}
