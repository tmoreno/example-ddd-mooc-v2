package com.tmoreno.mooc.backoffice.course.commands.publish;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class PublishCourseCommand implements Command<PublishCourseCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public PublishCourseCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(PublishCourseCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.publish();

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
