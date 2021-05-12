package com.tmoreno.mooc.backoffice.course.commands.changeImageUrl;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseChangeImageUrlCommand implements Command<CourseChangeImageUrlCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseChangeImageUrlCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseChangeImageUrlCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        CourseImageUrl imageUrl = new CourseImageUrl(params.getImageUrl());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeImageUrl(imageUrl);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
