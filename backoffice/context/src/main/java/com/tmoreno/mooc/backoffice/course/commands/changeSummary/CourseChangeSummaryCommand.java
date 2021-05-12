package com.tmoreno.mooc.backoffice.course.commands.changeSummary;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseSummary;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseChangeSummaryCommand implements Command<CourseChangeSummaryCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseChangeSummaryCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseChangeSummaryCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        CourseSummary summary = new CourseSummary(params.getSummary());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeSummary(summary);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
