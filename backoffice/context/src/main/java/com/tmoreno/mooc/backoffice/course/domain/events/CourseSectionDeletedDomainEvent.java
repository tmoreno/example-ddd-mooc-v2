package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseSectionDeletedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final SectionId sectionId;

    public CourseSectionDeletedDomainEvent(CourseId courseId, SectionId sectionId) {
        this.courseId = courseId;
        this.sectionId = sectionId;
    }

    @Override
    public String getEventName() {
        return "course.section.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public String getCourseId() {
        return courseId.getValue();
    }

    public String getSectionId() {
        return sectionId.getValue();
    }
}
