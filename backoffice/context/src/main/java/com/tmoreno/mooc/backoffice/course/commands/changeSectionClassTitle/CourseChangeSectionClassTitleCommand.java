package com.tmoreno.mooc.backoffice.course.commands.changeSectionClassTitle;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class CourseChangeSectionClassTitleCommand implements Command<CourseChangeSectionClassTitleCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public CourseChangeSectionClassTitleCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(CourseChangeSectionClassTitleCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        SectionId sectionId = new SectionId(params.getSectionId());
        SectionClassId sectionClassId = new SectionClassId(params.getSectionClassId());
        SectionClassTitle title = new SectionClassTitle(params.getTitle());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeSectionClassTitle(sectionId, sectionClassId, title);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
