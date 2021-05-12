package com.tmoreno.mooc.backoffice.course.commands.changeSectionClassDuration;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseChangeSectionClassDurationCommand implements Command<CourseChangeSectionClassDurationCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseChangeSectionClassDurationCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseChangeSectionClassDurationCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        SectionId sectionId = new SectionId(params.getSectionId());
        SectionClassId sectionClassId = new SectionClassId(params.getSectionClassId());
        DurationInSeconds duration = new DurationInSeconds(params.getDuration());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeSectionClassDuration(sectionId, sectionClassId, duration);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
