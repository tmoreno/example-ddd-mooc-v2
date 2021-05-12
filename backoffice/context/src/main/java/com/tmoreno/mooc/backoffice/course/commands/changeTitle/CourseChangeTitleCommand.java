package com.tmoreno.mooc.backoffice.course.commands.changeTitle;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseChangeTitleCommand implements Command<CourseChangeTitleCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseChangeTitleCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseChangeTitleCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        CourseTitle title = new CourseTitle(params.getTitle());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeTitle(title);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
