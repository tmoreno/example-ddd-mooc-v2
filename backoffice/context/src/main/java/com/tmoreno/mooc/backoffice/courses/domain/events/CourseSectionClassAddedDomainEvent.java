package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.courses.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.courses.domain.SectionId;
import com.tmoreno.mooc.backoffice.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;

public final class CourseSectionClassAddedDomainEvent extends DomainEvent {
    private final SectionId sectionId;
    private final SectionClassId sectionClassId;
    private final SectionClassTitle title;
    private final DurationInSeconds duration;

    public CourseSectionClassAddedDomainEvent(
            CourseId courseId,
            SectionId sectionId,
            SectionClassId sectionClassId,
            SectionClassTitle title,
            DurationInSeconds duration
    ) {
        super(courseId);

        this.sectionId = sectionId;
        this.sectionClassId = sectionClassId;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public String getEventName() {
        return "course.section.class.added";
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

    public SectionClassTitle getTitle() {
        return title;
    }

    public DurationInSeconds getDuration() {
        return duration;
    }
}
