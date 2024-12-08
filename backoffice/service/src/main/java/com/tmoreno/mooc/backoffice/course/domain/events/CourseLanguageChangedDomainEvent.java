package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseLanguageChangedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final Language language;

    public CourseLanguageChangedDomainEvent(CourseId courseId, Language language) {
        this.courseId = courseId;
        this.language = language;
    }

    @Override
    public String getEventName() {
        return "course.language.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public String getCourseId() {
        return courseId.getValue();
    }

    public String getLanguage() {
        return language.name();
    }
}
