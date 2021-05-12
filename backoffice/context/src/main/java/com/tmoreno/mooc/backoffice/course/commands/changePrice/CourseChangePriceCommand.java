package com.tmoreno.mooc.backoffice.course.commands.changePrice;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.events.EventBus;

import java.util.Currency;

public final class CourseChangePriceCommand implements Command<CourseChangePriceCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseChangePriceCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseChangePriceCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        Currency currency = Currency.getInstance(params.getCurrency());
        Price price = new Price(params.getValue(), currency);

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changePrice(price);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
