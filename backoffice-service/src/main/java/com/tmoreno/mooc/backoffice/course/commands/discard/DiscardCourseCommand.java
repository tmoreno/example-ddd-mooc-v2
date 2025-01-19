package com.tmoreno.mooc.backoffice.course.commands.discard;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class DiscardCourseCommand implements Command<DiscardCourseCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public DiscardCourseCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(DiscardCourseCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.discard();

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
