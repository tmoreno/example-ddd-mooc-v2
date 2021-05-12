package com.tmoreno.mooc.backoffice.course.commands.create;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CreateCourseCommand implements Command<CreateCourseCommandParams> {
    private final CourseRepository repository;
    private final EventBus eventBus;

    public CreateCourseCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CreateCourseCommandParams params) {
        CourseId id = new CourseId(params.getId());
        CourseTitle title = new CourseTitle(params.getTitle());

        Course course = Course.create(id, title);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
