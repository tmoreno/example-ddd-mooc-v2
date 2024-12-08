package com.tmoreno.mooc.backoffice.course.commands.deleteSection;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseDeleteSectionCommand implements Command<CourseDeleteSectionCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseDeleteSectionCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseDeleteSectionCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        SectionId sectionId = new SectionId(params.getSectionId());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.deleteSection(sectionId);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
