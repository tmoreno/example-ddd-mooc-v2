package com.tmoreno.mooc.backoffice.course.domain.events;

import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.events.DomainEvent;

public final class CoursePriceChangedDomainEvent extends DomainEvent {

    private final CourseId courseId;
    private final Price price;

    public CoursePriceChangedDomainEvent(CourseId courseId, Price price) {
        this.courseId = courseId;
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

    public String getCourseId() {
        return courseId.getValue();
    }

    public double getPriceValue() {
        return price.value();
    }

    public String getPriceCurrency() {
        return price.currency().getCurrencyCode();
    }
}
