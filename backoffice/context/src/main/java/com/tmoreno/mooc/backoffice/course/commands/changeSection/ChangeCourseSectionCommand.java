package com.tmoreno.mooc.backoffice.course.commands.changeSection;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseSectionNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.events.EventBus;

import java.util.Objects;

public final class ChangeCourseSectionCommand implements Command<ChangeCourseSectionCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public ChangeCourseSectionCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(ChangeCourseSectionCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        SectionId sectionId = new SectionId(params.getSectionId());
        SectionTitle title = params.getTitle() == null ? null : new SectionTitle(params.getTitle());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        Section section = course.getSections()
                .stream()
                .filter(s -> s.getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new CourseSectionNotFoundException(sectionId));

        if (title != null && !Objects.equals(title, section.getTitle())) {
            course.changeSectionTitle(sectionId, title);
        }

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
