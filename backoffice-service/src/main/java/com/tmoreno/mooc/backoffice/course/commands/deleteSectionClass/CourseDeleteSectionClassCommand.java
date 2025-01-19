package com.tmoreno.mooc.backoffice.course.commands.deleteSectionClass;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseDeleteSectionClassCommand implements Command<CourseDeleteSectionClassCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseDeleteSectionClassCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseDeleteSectionClassCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        SectionId sectionId = new SectionId(params.getSectionId());
        SectionClassId sectionClassId = new SectionClassId(params.getSectionClassId());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.deleteSectionClass(sectionId, sectionClassId);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
