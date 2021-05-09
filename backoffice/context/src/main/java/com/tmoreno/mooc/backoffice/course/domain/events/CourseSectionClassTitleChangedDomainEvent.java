package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseSectionClassTitleChangedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final SectionId sectionId;
    private final SectionClassId sectionClassId;
    private final SectionClassTitle title;

    public CourseSectionClassTitleChangedDomainEvent(
            CourseId courseId,
            SectionId sectionId,
            SectionClassId sectionClassId,
            SectionClassTitle title
    ) {
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.sectionClassId = sectionClassId;
        this.title = title;
    }

    @Override
    public String getEventName() {
        return "course.section.class.title.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseId getCourseId() {
        return courseId;
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
}
