package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseSectionClassAddedDomainEvent extends DomainEvent {

    private final CourseId courseId;
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
        this.courseId = courseId;
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

    public String getCourseId() {
        return courseId.getValue();
    }

    public String getSectionId() {
        return sectionId.getValue();
    }

    public String getSectionClassId() {
        return sectionClassId.getValue();
    }

    public String getTitle() {
        return title.getValue();
    }

    public int getDuration() {
        return duration.getValue();
    }
}
