package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseSectionDeletedDomainEvent extends DomainEvent {
    private final SectionId sectionId;

    public CourseSectionDeletedDomainEvent(CourseId courseId, SectionId sectionId) {
        super(courseId);

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

    public SectionId getSectionId() {
        return sectionId;
    }
}
