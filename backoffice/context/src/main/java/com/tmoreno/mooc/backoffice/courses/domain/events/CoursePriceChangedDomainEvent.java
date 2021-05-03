package com.tmoreno.mooc.backoffice.courses.domain.events;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.shared.domain.Price;
import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;

public final class CoursePriceChangedDomainEvent extends DomainEvent {
    private final Price price;

    public CoursePriceChangedDomainEvent(CourseId courseId, Price price) {
        super(courseId);

        this.price = price;
    }

    @Override
    public String getEventName() {
        return "course.price.changed";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    public Price getPrice() {
        return price;
    }
}
