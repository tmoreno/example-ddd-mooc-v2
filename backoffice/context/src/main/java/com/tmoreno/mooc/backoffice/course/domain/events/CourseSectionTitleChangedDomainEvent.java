package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseSectionTitleChangedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final SectionId sectionId;
    private final SectionTitle title;

    public CourseSectionTitleChangedDomainEvent(CourseId courseId, SectionId sectionId, SectionTitle title) {
        this.courseId = courseId;
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

    public CourseId getCourseId() {
        return courseId;
    }

    public SectionId getSectionId() {
        return sectionId;
    }

    public SectionTitle getTitle() {
        return title;
    }
}
