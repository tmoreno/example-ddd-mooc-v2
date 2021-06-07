package com.tmoreno.mooc.backoffice.course.commands.changeCourse;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseSummary;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.events.EventBus;

import java.util.Currency;

public final class ChangeCourseCommand implements Command<ChangeCourseCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public ChangeCourseCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(ChangeCourseCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        CourseTitle title = params.getTitle() == null ? null : new CourseTitle(params.getTitle());
        CourseImageUrl imageUrl = params.getImageUrl() == null ? null : new CourseImageUrl(params.getImageUrl());
        CourseSummary summary = params.getSummary() == null ? null : new CourseSummary(params.getSummary());
        CourseDescription description = params.getDescription() == null ? null : new CourseDescription(params.getDescription());
        Language language = params.getLanguage() == null ? null : Language.valueOf(params.getLanguage());
        Price price = params.getPriceValue() == null || params.getPriceCurrency() == null ? null : new Price(params.getPriceValue(), Currency.getInstance(params.getPriceCurrency()));

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeTitle(title);
        course.changeImageUrl(imageUrl);
        course.changeSummary(summary);
        course.changeDescription(description);
        course.changeLanguage(language);
        course.changePrice(price);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
