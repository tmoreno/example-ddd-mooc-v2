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

    public String getCourseId() {
        return courseId.getValue();
    }

    public String getSectionId() {
        return sectionId.getValue();
    }

    public String getTitle() {
        return title.value();
    }
}
