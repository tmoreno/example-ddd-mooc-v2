package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CourseImageChangedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final CourseImageUrl imageUrl;

    public CourseImageChangedDomainEvent(CourseId courseId, CourseImageUrl imageUrl) {
        this.courseId = courseId;
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

    public String getCourseId() {
        return courseId.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }
}
