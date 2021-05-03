package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionTitle;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseSectionTitleChangedDomainEvent extends DomainEvent {
    private final SectionId sectionId;
    private final SectionTitle title;

    public CourseSectionTitleChangedDomainEvent(CourseId courseId, SectionId sectionId, SectionTitle title) {
        super(courseId);

        this.sectionId = sectionId;
        this.title = title;
    }

    @Override
    public String getEventName() {
        return "course.section.title.changed";
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
