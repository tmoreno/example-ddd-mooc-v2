package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionId;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;

public final class CourseSectionClassDeletedDomainEvent extends DomainEvent {
    private final SectionId sectionId;
    private final SectionClassId sectionClassId;

    public CourseSectionClassDeletedDomainEvent(CourseId courseId, SectionId sectionId, SectionClassId sectionClassId) {
        super(courseId);

        this.sectionId = sectionId;
        this.sectionClassId = sectionClassId;
    }

    @Override
    public String getEventName() {
        return "course.section.class.deleted";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public SectionId getSectionId() {
        return sectionId;
    }

    public SectionClassId getSectionClassId() {
        return sectionClassId;
    }
}
