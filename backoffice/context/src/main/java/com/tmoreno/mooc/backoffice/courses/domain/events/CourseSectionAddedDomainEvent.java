package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionTitle;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;

public final class CourseSectionAddedDomainEvent extends DomainEvent {
    private final SectionId sectionId;
    private final SectionTitle title;

    public CourseSectionAddedDomainEvent(CourseId courseId, SectionId sectionId, SectionTitle title) {
        super(courseId);

        this.sectionId = sectionId;
        this.title = title;
    }

    @Override
    public String getEventName() {
        return "course.section.added";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public SectionId getSectionId() {
        return sectionId;
    }

    public SectionTitle getTitle() {
        return title;
    }
}
