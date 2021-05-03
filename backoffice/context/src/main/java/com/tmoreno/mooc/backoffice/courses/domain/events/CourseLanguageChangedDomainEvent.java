package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseLanguageChangedDomainEvent extends DomainEvent {
    private final Language language;

    public CourseLanguageChangedDomainEvent(CourseId courseId, Language language) {
        super(courseId);

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

    public Language getLanguage() {
        return language;
    }
}
