package com.tmoreno.mooc.backoffice.course.commands.changeLanguage;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseChangeLanguageCommand implements Command<CourseChangeLanguageCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseChangeLanguageCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseChangeLanguageCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        Language language = Language.valueOf(params.getLanguage());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeLanguage(language);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
