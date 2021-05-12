package com.tmoreno.mooc.backoffice.course.commands.changeDescription.changeSummary;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseChangeDescriptionCommand implements Command<CourseChangeDescriptionCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseChangeDescriptionCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseChangeDescriptionCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        CourseDescription description = new CourseDescription(params.getDescription());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeDescription(description);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
