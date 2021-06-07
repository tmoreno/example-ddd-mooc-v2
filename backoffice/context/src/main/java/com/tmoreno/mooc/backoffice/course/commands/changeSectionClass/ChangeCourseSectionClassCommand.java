package com.tmoreno.mooc.backoffice.course.commands.changeSectionClass;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.events.EventBus;

public final class ChangeCourseSectionClassCommand implements Command<ChangeCourseSectionClassCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public ChangeCourseSectionClassCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(ChangeCourseSectionClassCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        SectionId sectionId = new SectionId(params.getSectionId());
        SectionClassId sectionClassId = new SectionClassId(params.getSectionClassId());
        SectionClassTitle sectionClassTitle = params.getTitle() == null ? null : new SectionClassTitle(params.getTitle());
        DurationInSeconds duration = params.getDuration() == null ? null : new DurationInSeconds(params.getDuration());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeSectionClassTitle(sectionId, sectionClassId, sectionClassTitle);
        course.changeSectionClassDuration(sectionId, sectionClassId, duration);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
