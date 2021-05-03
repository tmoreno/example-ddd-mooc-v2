package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.CourseImageUrl;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;

public final class CourseImageChangedDomainEvent extends DomainEvent {
    private final CourseImageUrl imageUrl;

    public CourseImageChangedDomainEvent(CourseId courseId, CourseImageUrl imageUrl) {
        super(courseId);
        this.imageUrl = imageUrl;
    }

    @Override
    public String getEventName() {
        return "course.image.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public CourseImageUrl getImageUrl() {
        return imageUrl;
    }
}