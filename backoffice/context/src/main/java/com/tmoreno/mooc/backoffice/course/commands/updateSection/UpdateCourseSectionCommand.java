package com.tmoreno.mooc.backoffice.course.commands.updateSection;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

public final class UpdateCourseSectionCommand implements Command<UpdateCourseSectionCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public UpdateCourseSectionCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(UpdateCourseSectionCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        SectionId sectionId = new SectionId(params.getSectionId());
        SectionTitle title = params.getTitle() == null ? null : new SectionTitle(params.getTitle());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        course.changeSectionTitle(sectionId, title);

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
